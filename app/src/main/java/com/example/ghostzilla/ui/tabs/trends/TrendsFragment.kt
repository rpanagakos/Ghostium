package com.example.ghostzilla.ui.tabs.trends

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.observeAndSubmit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends) {

    private val viewModel: TrendsViewModel by viewModels()
    private val tabAdapter = TabsAdapter()

    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            this.adapter = tabAdapter
        }
        viewModel.getMarkets()
    }

    override fun observeViewModel() {
        //observeAndSubmit(viewModel.marketsLiveData, tabAdapter)
        viewModel.marketsLiveData.observe(viewLifecycleOwner, {
            tabAdapter.submitList(it.marketsList as List<LocalModel>?)
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

    }

    override fun stopOperations() {
    }
}