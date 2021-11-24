package com.example.ghostzilla.ui.tabs.trends

import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.models.coingecko.Markets
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : AbstractViewModel() {

    val marketsLiveData = SingleLiveEvent<Markets>()
    val coinUI = SingleLiveEvent<MarketsItem>()

    private lateinit var marketsDeferred: Deferred<Unit>
    private val coinLiveData = SingleLiveEvent<Coin>()

    fun getMarkets() {

        marketsDeferred = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(30)) {
            wrapEspressoIdlingResource {
                dataRepository.requestData().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            marketsLiveData.value = it
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
        if (marketsDeferred.isActive) marketsDeferred.cancel()
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            coinLiveData.value = it
                            coinUI.value = MarketsItem(
                                currentPrice = it.marketData.currentPrice.eur,
                                id = it.id,
                                image = it.image.thumb,
                                name = it.name,
                                priceChangePercentage24h = it.marketData.priceChangePercentage24h,
                                symbol = it.symbol
                            )
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        marketsDeferred.cancel()
    }

}