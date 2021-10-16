package com.example.ghostzilla.ui.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.models.pricing.PriceVolatility
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val covalentRemoteRepository: CovalentRemoteRepository,
) : ViewModel() {

    //mapping the response
    val internetConnection = SingleLiveEvent<Boolean>()
    val cryptosPricing = SingleLiveEvent<PriceVolatility>()

    //Api calls
    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                covalentRemoteRepository.getPriceVolatility()
            }.onFailure {
                //Show error handling message
                handleFailures(it)
            }.onSuccess { it ->
                when {
                    it == null -> {
                        //Show that something happened
                        handleFailures(null)
                    }
                    it.isSuccessful -> {
                        it.body()?.let {
                            it.data?.let {
                                cryptosPricing.postValue(it)
                            }
                        }
                    }
                    else -> {
                        //Show error handling message
                        handleFailures(null)
                    }
                }
            }
        }
    }


    private fun handleFailures(throwable: Throwable?) {
        when (throwable) {
            null -> {
                internetConnection.postValue(false)
            }
            is SocketTimeoutException -> {
                internetConnection.postValue(true)

            }
            is UnknownHostException -> {
                internetConnection.postValue(true)
            }
            else -> {
                internetConnection.postValue(false)
            }
        }
    }

}