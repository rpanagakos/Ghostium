package com.example.ghostzilla.ui.tabs.trends

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends) {

    private val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}