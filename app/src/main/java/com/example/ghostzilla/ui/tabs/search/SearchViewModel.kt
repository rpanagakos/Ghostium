package com.example.ghostzilla.ui.tabs.search

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.settings.RecentlyItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.common.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), GeneralClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    val searches = SingleLiveEvent<MutableList<RecentlyItem>>()

    val searchAdapter: TabsAdapter =
        TabsAdapter(currencyImpl = dataRepository.currencyImpl, generalClickListener = this)

    fun runOperation() {
        getSearches()
    }

    fun searchCoin(coinID: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
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
                        }
                    }
                }
            }

        }
    }

    private fun getSearches() {
        viewModelScope.launch {
            kotlin.runCatching {
                localRepository.fetchRecentlySearches()
            }.onSuccess {
                searchAdapter.submitList(it as List<LocalModel>)
            }
        }
    }

    override fun onClick(data: LocalModel, position: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                if (position == 0)
                    localRepository.deleteRecentItem(data as RecentlyItem)
                else
                    searchCoin((data as RecentlyItem).searchedText)
            }.onSuccess {
                if (position == 0) getSearches()
            }
        }
    }
}