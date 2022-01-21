package com.example.ghostzilla.ui.tabs.trends

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.models.errors.mapper.SEARCH_ERROR
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.*
import com.example.ghostzilla.utils.Constants.Companion.LOTTIE_FULL_STATE
import com.example.ghostzilla.utils.Constants.Companion.LOTTIE_STARTING_STATE
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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
    private val _priceData = mutableListOf<Entry>()
    val lineDataSet = SingleLiveEvent<LineDataSet>()
    val tabs = listOf("1d", "7d", "1m", "3m", "1y")

    fun runOperation(coinID: String) {
        if (networkConnectivity.isConnected()) {
            searchCoin(coinID)
            getChartsData(coinID, 1)
        } else {
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
                            _priceData.clear()
                            it.prices.forEach { priceItem ->
                                _priceData.add(
                                    Entry(
                                        getMyLongValue(priceItem[0]),
                                        getMyLongValue(priceItem[1])
                                    )
                                )
                            }
                            lineDataSet.postValue(LineDataSet(_priceData, "Price Range"))
                        } ?: kotlin.run { showToastMessage(SEARCH_ERROR) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }
        }
    }

    fun selectedTab(
        priceIndicator: View, dateIndicator: View, tabName: String, cryptoItem: CryptoItem
    ) {
        priceIndicator.dummyFadeOut()
        dateIndicator.dummyFadeOut()
        getChartsData(cryptoItem.id, convertMonthsToDays(tabName))
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

    fun favouriteOnClick(lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.isEnabled = false
        if (lottieAnimationView.progress == LOTTIE_FULL_STATE) {
            lottieAnimationView.reverseAnimationSpeed()
            lottieAnimationView.playAnimation()
        } else if (lottieAnimationView.progress == LOTTIE_STARTING_STATE) {
            lottieAnimationView.playAnimation()
        }
        lottieAnimationView.isEnabled = true
    }

}
