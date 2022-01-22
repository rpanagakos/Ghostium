package com.example.ghostzilla.ui.tabs.profile.favourite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    application: Application
) : AbstractViewModel(application) {

    var cryptos: LiveData<MutableList<CryptoItem>> =
        localRepository.fetchFavouriteCryptos().asLiveData()
    val favouriteAdapter: FavouriteAdapter = FavouriteAdapter()

    fun runOperation() {
        if (cryptos.value?.isNotEmpty() == true) {
            var cryptosIds = cryptos.value!![0].id
            cryptos.value!!.drop(1).forEach { cryptoItem ->
                cryptosIds = "$cryptosIds,${cryptoItem.id}"
            }
            getFavouriteCryptosPrices(cryptosIds)
            //favouriteAdapter.submitList(cryptos.value as List<LocalModel>)
        }
    }

    private fun getFavouriteCryptosPrices(cryptosIds: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getFavouritesPrices(cryptosIds).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let { responseJson ->
                            cryptos.value?.forEach { cryptoItem ->
                                cryptoItem.currentPrice = responseJson.get(cryptoItem.id).asJsonObject.get("eur").asDouble
                            }
                            favouriteAdapter.submitList(cryptos.value as List<LocalModel>)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            println(error)
                        }
                    }
                }
            }
        }
    }

    fun getAllNfts() {

    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

}
