package com.example.ghostzilla.ui.tabs.trends

import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.ui.DetailsActivity
import com.example.ghostzilla.ui.tabs.listeners.ActionTrendsListener
import com.example.ghostzilla.utils.BackToTopScrollListener
import com.example.ghostzilla.utils.changeImageOnEdittext
import com.example.ghostzilla.utils.removeWhiteSpaces
import com.example.ghostzilla.utils.searchQuery
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TrendsFragment :
    AbstractFragment<FragmentTrendsBinding, TrendsViewModel>(R.layout.fragment_trends) {

    override val viewModel: TrendsViewModel by activityViewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            this.adapter = viewModel.trendsAdapter
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg, requireContext()) {})
        }

        binding.searchEditText.apply {
            searchQuery()
                .debounce(350)
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

        viewModel.runOperation(object : ActionTrendsListener {
            override fun onClickDetails(
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
                            Pair.create(
                                contractName,
                                resources.getString(R.string.transition_coin_name)
                            ),
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

        })

        viewModel.networkConnectivity.registerNetworkCallback({
            if (binding.searchEditText.text.isNullOrEmpty() && (viewModel.marketsJob?.isCancelled == true || viewModel.marketsJob == null))
                viewModel.getMarkets()
            else if (!binding.searchEditText.text.isNullOrEmpty())
                viewModel.searchCoin(
                    binding.searchEditText.text.toString().lowercase().removeWhiteSpaces()
                )
        }, {
            if (viewModel.marketsJob?.isActive == true)
                viewModel.marketsJob?.cancel()
            viewModel.showToastMessage(NO_INTERNET_CONNECTION)

        })
    }

    override fun observeViewModel() {
        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })
    }

    override fun stopOperations() {
        viewModel.marketsJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.marketsJob?.isCancelled == true && binding.searchEditText.text.isNullOrEmpty())
            viewModel.getMarkets()
    }

    private fun updateListWithData() {
        viewModel.marketsLiveData.value?.let {
            viewModel.trendsAdapter.submitList(viewModel.marketsLiveData.value!!.marketsList as List<LocalModel>)
        }
        viewModel.getMarkets()
    }

}