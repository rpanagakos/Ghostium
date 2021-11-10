package com.example.ghostzilla.ui.tabs.trends

import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.pricing.PriceVolatility
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val covalentRemoteRepository: CovalentRemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AbstractViewModel() {

}