package com.example.ghostzilla.ui.tabs.cryptos

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentTrendsBinding
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendsFragment :
    AbstractFragment<FragmentTrendsBinding, TrendsViewModel>(R.layout.fragment_trends) {

    override val viewModel: TrendsViewModel by activityViewModels()

    @Inject
    lateinit var currencyImpl: CurrencyImpl

    override fun initLayout() {
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        }

        viewModel.networkConnectivity.registerNetworkCallback({
            viewModel.makeCallWhenOnline()
        }, {
            viewModel.displayInternetMessageWhenOffline()
        })
    }

    override fun observeViewModel() {
        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it as String, Toast.LENGTH_SHORT).show()
        })

        viewModel.trendingCryptos.observe(this, {
            viewModel.runOperation() { data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: ImageView ->
                when (data) {
                    is CryptoItem -> {
                        navigateToDetailsActivty(data, title, subTitle!!, circleImageView)
                    }
                }
            }
        })
    }

    override fun stopOperations() {
        viewModel.cryptosJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.cryptosJob?.isCancelled == true)
            viewModel.getAllCryptos()
    }

}