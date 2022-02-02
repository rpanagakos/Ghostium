package com.example.ghostzilla.ui.tabs.settings.general

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentGeneralSettingsBinding
import com.example.ghostzilla.di.CurrencyImpl
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
        binding.language = args.generalType == 0
        binding.title = if (args.generalType == 0) getString(R.string.option_language) else getString(R.string.option_currency)
        backButtonFavourite.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.runOperation(args.generalType == 0)
    }

    override fun observeViewModel() {
        viewModel.langChanged.observe(this, {
            binding.generalConstraint.visibility = View.GONE
            requireActivity().recreate()
            findNavController().popBackStack()
        })
    }

    override fun stopOperations() {
    }
}