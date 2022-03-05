package com.example.ghostzilla.ui.tabs.search

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.settings.RecentlyItem
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.common.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), GeneralClickListener, ItemOnClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity
    val displayMessage = MutableLiveData<Boolean>(false)

    private var callbacks: (
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) -> Unit = { _, _, _, _ -> }

    val searches = SingleLiveEvent<MutableList<RecentlyItem>>()
    val searchText = SingleLiveEvent<String?>()

    val searchAdapter: TabsAdapter =
        TabsAdapter(
            listener = this,
            currencyImpl = dataRepository.currencyImpl,
            generalClickListener = this
        )

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView,
            subTitle: TextView?,
            circleImageView: ImageView
        ) -> Unit
    ) {
        this.callbacks = listener
        getSearches()
    }

    fun searchCoin(coinID: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            displayMessage.value = false
                            val cryptoDetails = CryptoItem(
                                currentPrice = it.marketData.currentPrice.getPrice(dataRepository.currencyImpl.getCurrency()),
                                id = it.id,
                                image = it.image.thumb,
                                name = it.name,
                                priceChangePercentage24h = it.marketData.priceChangePercentage24h,
                                symbol = it.symbol
                            )
                            localRepository.insertRecentItem(RecentlyItem(it.id))
                            searchAdapter.submitList(listOf(cryptoDetails) as List<LocalModel>)
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

    fun getSearches() {
        viewModelScope.launch {
            kotlin.runCatching {
                localRepository.fetchRecentlySearches()
            }.onSuccess {
                if (it.isEmpty()) {
                    resultNotFound.postValue(1)
                    displayMessage.postValue(true)
                }
                else
                {
                    val list = mutableListOf<LocalModel>(TitleRecyclerItem(context.getString(R.string.recently_searches)))
                    searchAdapter.submitList(list.plus(it))
                }
            }
        }
    }

    fun clearSearch() {
        getSearches()
        displayMessage.postValue(false)
    }

    override fun onClick(data: LocalModel, position: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                if (position == 0)
                    localRepository.deleteRecentItem(data as RecentlyItem)
                else
                    searchText.postValue((data as RecentlyItem).searchedText)
            }.onSuccess {
                if (position == 0) getSearches()
            }
        }
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) {
        callbacks.invoke(data, title, subTitle, circleImageView)
    }
}