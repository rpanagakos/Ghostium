package com.example.ghostzilla.ui.tabs.nft

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NftsViewModel @Inject constructor( private val dataRepository: DataRepository, application: Application) : AbstractViewModel(application){

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun runOperation(){
        getAllNfts()
    }

    fun getAllNfts(){
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getAllNfts().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->

                        }
                    }
                }
            }
        }
    }

}