package com.example.ghostzilla.ui.tabs.trends

import android.view.View
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
    private var currentPosition : Int = 0
    private val tabAdapter = TabsAdapter{ it ->
        currentPosition = it
        if (currentPosition > 18)
            binding.backToTop.visibility = View.VISIBLE
        else
            binding.backToTop.visibility = View.GONE
    }

    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            this.adapter = tabAdapter
            showShimmer()
        }

        binding.backToTop.setOnClickListener {
            if (currentPosition > 18) {
                binding.contractsTrendsRecycler.scrollToPosition(12)
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
            } else
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
        }

        viewModel.getMarkets()
    }

    override fun observeViewModel() {
        //observeAndSubmit(viewModel.marketsLiveData, tabAdapter)
        viewModel.marketsLiveData.observe(viewLifecycleOwner, {
            tabAdapter.submitList(it.marketsList as List<LocalModel>?)
            binding.contractsTrendsRecycler.hideShimmer()
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

    }

    override fun stopOperations() {
    }
}