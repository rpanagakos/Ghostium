package com.example.ghostzilla.ui.tabs.trends

import android.view.View
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
    private var currentItemPosition: Int = 0
   /* private val tabAdapter: TabsAdapter = TabsAdapter {
        currentItemPosition = it
        if (it > 20)
            binding.backToTop.visibility = View.VISIBLE
        else
            binding.backToTop.visibility = View.GONE
    }*/

    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            //this.adapter = tabAdapter
            showShimmer()
        }

        binding.backToTop.setOnClickListener {/*
            if (currentItemPosition > 25) {
                binding.contractsTrendsRecycler.scrollToPosition(15)
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
            } else
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)*/
        }
        //viewModel.getCryptoPricing()
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}