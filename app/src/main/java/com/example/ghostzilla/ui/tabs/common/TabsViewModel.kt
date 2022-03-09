package com.example.ghostzilla.ui.tabs.common

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application) {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity


    val displayInternetMessage = MutableLiveData<Boolean>(false)
    var trendingCryptos: LiveData<MutableList<TredingCoins>> =
        localRepository.fetchTrendingCryptos().asLiveData()
    private val time24h = 48200

    fun runOperation() {
        if (trendingCryptos.value == null || trendingCryptos.value!!.isEmpty() || trendingCryptos.value!![0].timetamps + time24h <= System.currentTimeMillis())
            getCryptos()
        networkConnectivity.registerNetworkCallback({
            displayInternetMessage.postValue(false)
        }, {
            displayInternetMessage.postValue(true)
        })
    }

    fun getCryptos() {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getTredingCryptos().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            it.timetamps = System.currentTimeMillis()
                            localRepository.insertTrendingCoins(it)
                        } ?: run { showToastMessage(0) }
                    }
                }
            }
        }
    }

}