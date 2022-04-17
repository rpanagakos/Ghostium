package com.rdp.ghostium.ui.tabs.nft

import androidx.fragment.app.viewModels
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.databinding.FragmentNftsBinding
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