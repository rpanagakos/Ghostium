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
import com.example.ghostzilla.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class TrendsFragment : AbstractFragment<FragmentTrendsBinding>(R.layout.fragment_trends),
    ItemOnClickListener {

    private val viewModel: TrendsViewModel by viewModels()
    private var currentPosition: Int = 0
    private val tabAdapter = TabsAdapter(this)

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            this.adapter = tabAdapter
            setHasFixedSize(true)
            addOnScrollListener(object : BackToTopScrollListener(binding) {})
        }

        binding.backToTopImg.setOnClickListener {
            if (currentPosition > 18) {
                binding.contractsTrendsRecycler.scrollToPosition(12)
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
            } else
                binding.contractsTrendsRecycler.smoothScrollToPosition(0)
        }

        binding.searchButton.setOnClickListener {
            //i dont like it
            when (binding.searchEditText.text?.isEmpty()) {
                true -> {
                    binding.searchEditText.apply {
                        requestFocus()
                        showKeyboard()
                    }
                }
                else -> {
                    binding.searchEditText.clearTextAndFocus(this)
                    binding.searchButton.setImageResource(R.drawable.ic_search)
                    updateListWithData()
                }
            }
        }

        binding.searchEditText.apply {
            //i dont like it
            searchQuery()
                .debounce(600)
                .onEach {
                    binding.searchButton.changeImageOnEdittext(
                        binding.searchEditText,
                        R.drawable.ic_search,
                        R.drawable.ic_outline_clear
                    )
                    if (viewModel.markets.isActive) viewModel.markets.cancel()
                    if (!this.text.isNullOrEmpty())
                        viewModel.searchCoin(this.text.toString().lowercase().removeWhiteSpaces())
                    else
                        updateListWithData()
                }
                .launchIn(lifecycleScope)
        }

        viewModel.getMarkets()
    }

    override fun observeViewModel() {
        viewModel.marketsLiveData.observe(viewLifecycleOwner, {
            tabAdapter.submitList(it.marketsList as List<LocalModel>)
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

        viewModel.coinUI.observe(viewLifecycleOwner, {
            tabAdapter.submitList(listOf(it) as List<LocalModel>)
        })

    }

    override fun stopOperations() {
    }

    override fun onClick(data: LocalModel) {
        when (data) {
            is MarketsItem -> Toast.makeText(requireContext(), data.id, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateListWithData() {
        tabAdapter.submitList(viewModel.marketsLiveData.value?.marketsList as List<LocalModel>)
        viewModel.markets.start()
    }
}