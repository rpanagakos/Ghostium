package com.example.ghostzilla.ui.tabs.wallet

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentWalletBinding
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel

class WalletFragment : AbstractFragment<FragmentWalletBinding, TrendsViewModel>(R.layout.fragment_wallet) {

    override val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}