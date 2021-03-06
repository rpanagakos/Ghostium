package com.rdp.ghostium.ui.tabs.cryptos

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.coin.Coin
import com.rdp.ghostium.models.errors.mapper.NO_INTERNET_CONNECTION
import com.rdp.ghostium.models.errors.mapper.SEARCH_ERROR
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.utils.*
import com.rdp.ghostium.utils.Constants.Companion.LOTTIE_STARTING_STATE
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.network.DataRepositorySource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource,
    private val localRepository: LocalRepository,
    private val currencyImpl: CurrencySource,
    application: Application
) : AbstractViewModel(application) {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity
    val isFavourite = SingleLiveEvent<Boolean>()
    val isLoading = SingleLiveEvent<Boolean>()
    val price = SingleLiveEvent<Double>()

    val cryptoDetails = SingleLiveEvent<Coin>()
    private val _priceData = mutableListOf<Entry>()
    val lineDataSet = SingleLiveEvent<LineDataSet>()
    val tabs = listOf(context.getString(R.string.first_day), context.getString(R.string.seven_days), context.getString(
            R.string.one_month), context.getString(R.string.three_months), context.getString(R.string.one_year))
    
    lateinit var cryptoItem: CryptoItemDB

    fun runOperation(coinID: String) {
        isCryptoInDB(coinID)
        if (networkConnectivity.isConnected()) {
            searchCoin(coinID)
            getChartsData(coinID, 1)
        } else {
            checkErrorCode(NO_INTERNET_CONNECTION)
        }
    }

    private fun isCryptoInDB(coinID: String) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localRepository.isFavourite(coinID)
            }.onSuccess {
                isFavourite.postValue(it)
            }
        }
    }

    private fun searchCoin(coinID: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            cryptoDetails.postValue(it)
                            price.postValue(it.marketData.currentPrice.getPrice(currencyImpl.getCurrency()))
                            isLoading.postValue(false)
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

    fun selectedTab(tabName: String, cryptoItem: CryptoItem) {
        getChartsData(cryptoItem.id, convertMonthsToDays(tabName))
    }

    fun visitCryptoSite(site: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(site)
        i.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, i, null)
    }

    fun favouriteOnClick(lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.disable()
        if (lottieAnimationView.progress > LOTTIE_STARTING_STATE) {
            viewModelScope.launch(Dispatchers.Default) {
                kotlin.runCatching {
                    localRepository.deleteCrypto(cryptoItem)
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        lottieAnimationView.progress = 0f
                        lottieAnimationView.enable()
                    }
                }.onFailure {
                    lottieAnimationView.enable()
                }
            }
        } else if (lottieAnimationView.progress == LOTTIE_STARTING_STATE) {
            viewModelScope.launch(Dispatchers.Default) {
                kotlin.runCatching {
                    localRepository.insertFavouriteCrypto(cryptoItem)
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        lottieAnimationView.enableAfterAnimation()
                    }
                }.onFailure {
                    lottieAnimationView.enable()
                }
            }
        }
    }

}
