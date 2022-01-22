package com.example.ghostzilla.ui.tabs.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentOptionBinding
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionFragment :
    AbstractFragment<FragmentOptionBinding, SettingsViewModel>(R.layout.fragment_option) {

    private val args: OptionFragmentArgs by navArgs()

    override val viewModel: SettingsViewModel by viewModels()


    override fun initLayout() {
        binding.title = args.title
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }

}