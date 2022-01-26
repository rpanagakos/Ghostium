package com.example.ghostzilla.ui.tabs.settings.general

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentGeneralSettingsBinding
import javax.inject.Inject

class GeneralSettingsFragment :
    AbstractFragment<FragmentGeneralSettingsBinding, GeneralSettingsViewModel>(R.layout.fragment_general_settings) {

    override val viewModel: GeneralSettingsViewModel by viewModels()

    @Inject
    lateinit var dataStore: DataStoreUtil

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}