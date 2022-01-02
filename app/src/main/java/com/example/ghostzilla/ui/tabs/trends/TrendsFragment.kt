package com.example.ghostzilla.ui.tabs.trends

import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.errors.mapper.NOT_FOUND
import com.example.ghostzilla.ui.DetailsActivity
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.*
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
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


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            this.adapter = tabAdapter
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg, requireContext()) {})
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
            binding.displayError = false
            tabAdapter.submitList(it.marketsList as List<LocalModel>)
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

        viewModel.coinUI.observe(viewLifecycleOwner, {
            binding.displayError = false
            tabAdapter.submitList(listOf(it) as List<LocalModel>)
        })

        viewModel.resultNotFound.observe(viewLifecycleOwner, {
            //i dont like that
            if (it == NOT_FOUND) {
                binding.lottieImage.setAnimation("nothing_found.json")
                binding.errorText.text = getString(R.string.nothing_found)
            } else {
                binding.lottieImage.setAnimation("internet_connection.json")
                binding.errorText.text = getString(R.string.no_internet_connection)
            }
            binding.displayError = true
            binding.lottieImage.playAnimation()
        })

    }

    override fun stopOperations() {
        viewModel.marketsDeferred.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.marketsDeferred.isCancelled)
            viewModel.getMarkets()
    }

    private fun updateListWithData() {
        viewModel.marketsLiveData.value?.let {
            tabAdapter.submitList(viewModel.marketsLiveData.value!!.marketsList as List<LocalModel>)
        }
        viewModel.getMarkets()
    }

    override fun onClick(
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) {
        when (data) {
            is MarketsItem -> {
                val intent = Intent(requireActivity(), DetailsActivity::class.java).apply {
                    putExtra("coin", data)
                }
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    Pair.create(contractName, resources.getString(R.string.transition_coin_name)),
                    Pair.create(
                        contractTickerSumbol,
                        resources.getString(R.string.transition_coin_symbol)
                    ),
                    Pair.create(
                        circleImageView,
                        resources.getString(R.string.transition_coin_image)
                    )
                )
                startActivity(intent, options.toBundle())
                requireActivity().window.exitTransition = null
            }
        }
    }
}