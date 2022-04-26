package com.rdp.ghostium.ui.tabs.common

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.connectivity.ConnectivityObserver
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    connectivityObserver: ConnectivityObserver,
    application: Application
) : AbstractViewModel(application, connectivityObserver) {

    var trendingCryptos: LiveData<MutableList<TredingCoins>> =
        localRepository.fetchTrendingCryptos().asLiveData()
    private val time24h = 48200

    init {
        observeConnectivity()
    }

    fun runOperation() {
        if (trendingCryptos.value == null || trendingCryptos.value!!.isEmpty() || trendingCryptos.value!![0].timetamps + time24h <= System.currentTimeMillis() / 1000)
            getCryptos()
    }

    fun getCryptos() {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getTredingCryptos().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            it.timetamps = System.currentTimeMillis() / 1000
                            localRepository.insertTrendingCoins(it)
                        } ?: run { showToastMessage(0) }
                    }
                }
            }
        }
    }

}