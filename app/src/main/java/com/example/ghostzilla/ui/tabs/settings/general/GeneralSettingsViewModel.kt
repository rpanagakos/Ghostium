package com.example.ghostzilla.ui.tabs.settings.general

import android.app.Application
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.network.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application) {


}
