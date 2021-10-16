package com.example.ghostzilla.ui.tabs

import androidx.lifecycle.ViewModelProvider
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AbstractFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: TrendsViewModel by lazy {
        ViewModelProvider(this).get(TrendsViewModel::class.java)
    }

    override fun initLayout() {
        viewModel.getCurrentWeather()
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}
}