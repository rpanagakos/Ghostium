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
import com.example.ghostzilla.ui.DetailsActivity
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
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg, requireContext()) {})
        }

        binding.searchLayout.searchEditText.apply {
            searchQuery()
                .debounce(350)
                .onEach {
                    binding.searchLayout.searchButton.changeImageOnEdittext(
                        binding.searchLayout.searchEditText,
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

        viewModel.runOperation() { data: LocalModel,
                                   contractName: TextView,
                                   contractTickerSumbol: TextView,
                                   circleImageView: CircleImageView ->
            when (data) {
                is MarketsItem -> {
                    val intent = Intent(requireActivity(), DetailsActivity::class.java).apply {
                        putExtra("coin", data)
                    }
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        Pair.create(
                            contractName, getString(R.string.transition_coin_name)
                        ),
                        Pair.create(
                            contractTickerSumbol, getString(R.string.transition_coin_symbol)
                        ),
                        Pair.create(
                            circleImageView, getString(R.string.transition_coin_image)
                        )
                    )
                    startActivity(intent, options.toBundle())
                    requireActivity().window.exitTransition = null
                }
            }
        }

        viewModel.networkConnectivity.registerNetworkCallback({
            //vasili se syxainomai <3
           binding.searchLayout.searchEditText.text?.let { viewModel.makeCallWhenOnline(it.toString()) }
        }, {
            viewModel.displayInternetMessageWhenOffline()
        })
    }

    override fun observeViewModel() {
        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })
    }

    override fun stopOperations() {
        viewModel.cryptosJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.cryptosJob?.isCancelled == true && binding.searchLayout.searchEditText.text.isNullOrEmpty())
            viewModel.getAllCryptos()
    }

    private fun updateListWithData() {
        viewModel.cryptosLiveData.value?.let {
            viewModel.trendsAdapter.submitList(viewModel.cryptosLiveData.value!!.marketsList as List<LocalModel>)
        }
        viewModel.getAllCryptos()
    }

}