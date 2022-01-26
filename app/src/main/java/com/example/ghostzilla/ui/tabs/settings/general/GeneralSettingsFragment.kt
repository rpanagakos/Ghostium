package com.example.ghostzilla.ui.tabs.settings.general

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentGeneralSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*
import javax.inject.Inject

@AndroidEntryPoint
class GeneralSettingsFragment :
    AbstractFragment<FragmentGeneralSettingsBinding, GeneralSettingsViewModel>(R.layout.fragment_general_settings) {

    override val viewModel: GeneralSettingsViewModel by viewModels()

    private val args: GeneralSettingsFragmentArgs by navArgs()

    @Inject
    lateinit var dataStore: DataStoreUtil

    override fun initLayout() {
        binding.title = args.title
        backButtonFavourite.setOnClickListener { findNavController().popBackStack() }
        viewModel.runOperation(args.title == getString(R.string.option_language))
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}