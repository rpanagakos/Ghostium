package com.example.ghostzilla.ui.tabs.trends

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.changeImageOnEdittext
import com.example.ghostzilla.utils.clearTextAndFocus
import com.example.ghostzilla.utils.searchQuery
import com.example.ghostzilla.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.recyclerview.widget.OrientationHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends), ItemOnClickListener {

    private val viewModel: TrendsViewModel by viewModels()
    private var currentPosition: Int = 0
    private val tabAdapter = TabsAdapter(this) { it ->
        currentPosition = it
        binding.backToTop = currentPosition > 18
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            this.adapter = tabAdapter
            setHasFixedSize(true)
            showShimmer()
        }

        binding.backToTopImg.setOnClickListener {
            if (currentPosition > 18) {
                binding.contractsTrendsRecycler.scrollToPosition(12)
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
            } else
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(500)
                viewModel.getMarkets()
            }
        }

        binding.searchButton.setOnClickListener {
            if (binding.searchEditText.text?.isEmpty() == true)
                binding.searchEditText.apply {
                    requestFocus()
                    showKeyboard()
                }
            else {
                binding.searchEditText.clearTextAndFocus(this)
                binding.searchButton.setImageResource(R.drawable.ic_search)
            }
        }

        binding.searchEditText.apply {
            searchQuery()
                .debounce(600)
                .onEach {
                    binding.searchButton.changeImageOnEdittext(
                        binding.searchEditText,
                        R.drawable.ic_search,
                        R.drawable.ic_outline_clear
                    )
                }
                .launchIn(lifecycleScope)
        }

        viewModel.getMarkets()
    }

    override fun observeViewModel() {
        viewModel.marketsLiveData.observe(viewLifecycleOwner, {
            tabAdapter.submitList(it.marketsList as List<LocalModel>?)
            binding.contractsTrendsRecycler.hideShimmer()
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

    }

    override fun stopOperations() {
    }

    override fun onClick(data: LocalModel) {
        when (data) {
            is MarketsItem -> Toast.makeText(requireContext(), data.id, Toast.LENGTH_SHORT).show()
        }
    }
}