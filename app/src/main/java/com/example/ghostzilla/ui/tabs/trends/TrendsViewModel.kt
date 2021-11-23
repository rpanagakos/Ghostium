package com.example.ghostzilla.ui.tabs.trends

import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.models.coingecko.Markets
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
    val coinLiveData = SingleLiveEvent<Coin>()
    private lateinit var markets: Deferred<Unit>

    fun getMarkets() {
        markets = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(30)) {
            wrapEspressoIdlingResource {
                dataRepository.requestData().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            marketsLiveData.value = it
                        }
                            ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            showToastMessage(
                                error
                            )
                        }
                    }
                }
            }
        }
    }

    fun searchCoin(coinID: String) {
        markets.cancel()
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            coinLiveData.value = it
                        }
                            ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            showToastMessage(
                                error
                            )
                        }
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        markets.cancel()
    }

}