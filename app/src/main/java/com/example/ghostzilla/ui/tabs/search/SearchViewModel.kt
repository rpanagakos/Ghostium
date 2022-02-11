package com.example.ghostzilla.ui.tabs.search

import android.app.Application
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.network.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application) {

    fun runOperation(){

    }
}