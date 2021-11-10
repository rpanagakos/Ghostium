package com.example.ghostzilla.ui.tabs.trends

import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val covalentRemoteRepository: CovalentRemoteRepository,
) : AbstractViewModel() {

}