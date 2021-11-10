package com.example.ghostzilla.ui.tabs.trends

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends) {

    private val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
        viewModel.getMarkets()
    }

    override fun observeViewModel() {
        viewModel.showToast.observe(this, Observer {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })
    }

    override fun stopOperations() {
    }
}