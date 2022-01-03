package com.example.ghostzilla.ui.tabs.profile

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentProfileBinding
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel


class ProfileFragment : AbstractFragment<FragmentProfileBinding, TrendsViewModel>(R.layout.fragment_profile) {

    override val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}