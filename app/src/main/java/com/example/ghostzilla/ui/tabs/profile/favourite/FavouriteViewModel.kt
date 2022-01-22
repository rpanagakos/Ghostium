package com.example.ghostzilla.ui.tabs.profile.favourite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
            favouriteAdapter.submitList(cryptos.value as List<LocalModel>)
        }
    }

    private fun getFavouriteCryptosPrices(cryptosIds: String) {

    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

}
