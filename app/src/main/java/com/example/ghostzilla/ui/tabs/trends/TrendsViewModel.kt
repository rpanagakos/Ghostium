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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : AbstractViewModel() {

    val marketsLiveData  = SingleLiveEvent<GenericResponse<Markets>>()

    fun getMarkets(){
        viewModelScope.launch {
            dataRepository.requestData().collect {
                marketsLiveData.value = it
            }
        }
    }


}