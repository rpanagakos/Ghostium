package com.example.ghostzilla.ui.tabs.trends

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.models.coingecko.charts.CoinPrices
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.models.errors.mapper.SEARCH_ERROR
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application) {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    val cryptoDetails = SingleLiveEvent<Coin>()
    val chartData = SingleLiveEvent<CoinPrices>()

    fun runOperation(coinID: String) {
        if (networkConnectivity.isConnected()) {
            searchCoin(coinID)
            getChartsData(coinID, 7)
        }
        else {
            checkErrorCode(NO_INTERNET_CONNECTION)
        }
    }

    private fun searchCoin(coinID: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            cryptoDetails.postValue(it)
                        } ?: run { showToastMessage(SEARCH_ERROR) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }

        }
    }

    fun getChartsData(coinID: String, days: Int) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getCoinChartDetails(coinID, days).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            chartData.postValue(it)
                        } ?: kotlin.run { showToastMessage(SEARCH_ERROR) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }
        }
    }

    fun visitCryptoSite(site: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(site)
        i.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, i, null)
    }

    fun displayInternetMessageWhenOffline() {
        showToastMessage(NO_INTERNET_CONNECTION)
    }

}
