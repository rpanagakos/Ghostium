package com.example.ghostzilla.ui.tabs.trends

import android.app.Application
import android.text.Editable
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.Markets
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    private var callbacks: (
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) -> Unit = { _, _, _, _ -> }
    val trendsAdapter: TabsAdapter = TabsAdapter(this)

    val marketsLiveData = SingleLiveEvent<Markets>()
    val coinUI = SingleLiveEvent<MarketsItem>()
    val displayMessage = MutableLiveData<Boolean>(false)

    var marketsJob: Job? = null

    fun runOperation(
        listener: (
            data: LocalModel,
            contractName: TextView,
            contractTickerSumbol: TextView,
            circleImageView: CircleImageView
        ) -> Unit
    ) {
        this.callbacks = listener
        if (networkConnectivity.isConnected())
            getMarkets()
        else {
            checkErrorCode(NO_INTERNET_CONNECTION)
            displayMessage.value = true
        }
    }

    fun getMarkets() {
        //add connectivity check
        if ((marketsJob?.isActive == false || marketsJob == null) && networkConnectivity.isConnected()) {
            marketsJob = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(30)) {
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
                                displayMessage.value = true
                            }
                        }
                    }
                }
            }
        }
    }

    fun searchCoin(coinID: String) {
        if (marketsJob?.isActive == true)
            marketsJob?.cancel()
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

    fun searchButton(searchButton: ImageView, searchEditText: TextInputEditText) {
        when (searchEditText.text?.isEmpty()) {
            true -> {
                searchEditText.apply {
                    requestFocus()
                    showKeyboard()
                }
            }
            else -> {
                searchEditText.clearTextAndFocus(context)
                searchButton.setImageResource(R.drawable.ic_search)
            }
        }
    }

    fun makeCallWhenOnline(inputText: String) {
        if (inputText.isEmpty() && (marketsJob?.isCancelled == true || marketsJob == null))
            getMarkets()
        else if (inputText.isNotEmpty())
            searchCoin(
                inputText.lowercase().removeWhiteSpaces()
            )
    }

    fun displayInternetMessageWhenOffline() {
        if (marketsJob?.isActive == true)
            marketsJob?.cancel()
        showToastMessage(NO_INTERNET_CONNECTION)
    }

    override fun onCleared() {
        super.onCleared()
        marketsJob?.cancel()
    }

    override fun onClick(
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) {
        callbacks.invoke(data, contractName, contractTickerSumbol, circleImageView)
    }

}
