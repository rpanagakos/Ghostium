package com.example.ghostzilla.ui.tabs.trends

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.Markets
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.ui.tabs.listeners.ActionTrendsListener
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : AbstractViewModel(), ItemOnClickListener {

    private var callbacks: ActionTrendsListener? = null
    val trendsAdapter: TabsAdapter = TabsAdapter(this)

    val marketsLiveData = SingleLiveEvent<Markets>()
    val coinUI = SingleLiveEvent<MarketsItem>()
    val displayMessage = MutableLiveData<Boolean>(false)

    lateinit var marketsDeferred: Job

    fun runOperation(listener: ActionTrendsListener) {
        this.callbacks = listener
        getMarkets()
    }

    fun finishOperations() {
        this.callbacks = null
    }

    fun getMarkets() {
        marketsDeferred = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(30)) {
            wrapEspressoIdlingResource {
                dataRepository.requestData().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            displayMessage.value = false
                            marketsLiveData.value = it
                            trendsAdapter.submitList(it.marketsList as List<LocalModel>)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }
        }
    }

    fun searchCoin(coinID: String) {
        if (marketsDeferred.isActive)
            marketsDeferred.cancel()
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            displayMessage.value = false
                            coinUI.value = MarketsItem(
                                currentPrice = it.marketData.currentPrice.eur,
                                id = it.id,
                                image = it.image.thumb,
                                name = it.name,
                                priceChangePercentage24h = it.marketData.priceChangePercentage24h,
                                symbol = it.symbol
                            )
                            trendsAdapter.submitList(listOf(coinUI.value) as List<LocalModel>)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                            displayMessage.value = true
                        }
                    }
                }
            }

        }
    }

    fun scrollToTopRecycler(recyclerView: RecyclerView) {
        when ((recyclerView.adapter as? TabsAdapter)?.currentPosition?.get()) {
            in 0..18 -> Unit
            else -> recyclerView.scrollToPosition(18)
        }
        recyclerView.smoothScrollToPosition(0)
    }

    /*fun searchButton(searchButton: ImageView, searchEditText: TextInputEditText) {
        when (searchEditText.text?.isEmpty()) {
            true -> {
                searchEditText.apply {
                    requestFocus()
                    showKeyboard()
                }
            }
            else -> {
                searchButton.setImageResource(R.drawable.ic_search)
                getMarkets()
            }
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        marketsDeferred.cancel()
    }

    override fun onClick(
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) {
        callbacks?.onClickDetails(data, contractName, contractTickerSumbol, circleImageView)
    }

}
