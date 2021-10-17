package com.example.ghostzilla.ui.tabs.trends

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.ui.tabs.TabsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends) {

    private val viewModel: TrendsViewModel by viewModels()
    private val tabAdapter: TabsAdapter = TabsAdapter { }

    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            this.adapter = tabAdapter
            showShimmer()
        }
        viewModel.getCryptoPricing()
    }

    override fun observeViewModel() {
        viewModel.cryptosPricing.observe(viewLifecycleOwner, Observer {
            when {
                it.items.isNotEmpty() -> {
                    tabAdapter.submitList(it.items)
                }
                else -> {
                    //display a message or something
                }
            }
            binding.contractsTrendsRecycler.hideShimmer()
        })
    }

    override fun stopOperations() {
    }
}