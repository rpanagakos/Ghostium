package com.example.ghostzilla.ui.tabs.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentOptionBinding
import com.example.ghostzilla.ui.tabs.profile.favourite.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_option.*

@AndroidEntryPoint
class OptionFragment :
    AbstractFragment<FragmentOptionBinding, FavouriteViewModel>(R.layout.fragment_option) {

    private val args: OptionFragmentArgs by navArgs()

    override val viewModel: FavouriteViewModel by viewModels()

    override fun initLayout() {
        binding.title = args.title
        backButtonFavourite.setOnClickListener { findNavController().popBackStack() }
    }

    override fun observeViewModel() {
        viewModel.cryptos.observe(this, { viewModel.runOperation() })
    }

    override fun stopOperations() {
    }

}