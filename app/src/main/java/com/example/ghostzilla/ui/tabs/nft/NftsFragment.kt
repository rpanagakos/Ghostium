package com.example.ghostzilla.ui.tabs.nft

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentNftsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftsFragment : AbstractFragment<FragmentNftsBinding, NftsViewModel>(R.layout.fragment_nfts) {

    override val viewModel: NftsViewModel by viewModels()

    override fun initLayout() {
        viewModel.runOperation()
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}