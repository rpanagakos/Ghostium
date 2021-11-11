package com.example.ghostzilla.ui.tabs.trends

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.models.coingecko.Markets
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : AbstractViewModel() {

    val marketsLiveData  = SingleLiveEvent<Markets>()

    fun getMarkets(){
        viewModelScope.launch {
            dataRepository.requestData().collect {
                when(it){
                    is GenericResponse.Success -> it.data?.let {  marketsLiveData.value = it } ?: run { showToastMessage(0)  }
                    is GenericResponse.DataError -> it.errorCode?.let {error -> showToastMessage(error)  }
                }
            }
        }
    }


}