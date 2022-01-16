package com.example.ghostzilla.ui.tabs.profile

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    AbstractFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun initLayout() {
        viewModel.runOperation()
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}