package com.example.ghostzilla.ui.tabs.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentHomeBinding
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AbstractFragment<FragmentHomeBinding, TrendsViewModel>(R.layout.fragment_home) {

    override val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}
}