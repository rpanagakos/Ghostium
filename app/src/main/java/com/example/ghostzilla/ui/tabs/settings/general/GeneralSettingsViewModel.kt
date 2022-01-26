package com.example.ghostzilla.ui.tabs.settings.general

import android.app.Application
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.models.settings.LanguageItem
import com.example.ghostzilla.network.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application), GeneralClickListener {

    val generalAdapter = GeneralSettingsAdapter(this, this)
    private val langList = listOf(
        LanguageItem("English", true),
        LanguageItem("Deutsch", false),
        LanguageItem("Italiano", false),
        LanguageItem("Español", false),
        )

    fun runOperation() {
        generalAdapter.submitList(langList)

    }

    override fun onClick(data: LocalModel) {
        when(data){
            is LanguageItem -> {}
            else -> {}
        }
    }


}
