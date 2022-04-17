package com.rdp.ghostium.ui.tabs.cryptos

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentTrendsBinding
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.tredings.Item
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendsFragment :
    AbstractFragment<FragmentTrendsBinding, TrendsViewModel>(R.layout.fragment_trends) {

    override val viewModel: TrendsViewModel by activityViewModels()

    //remove
    @Inject
    lateinit var currencyImpl: CurrencyImpl

    override fun initLayout() {
        viewModel.trendingTitle.postValue(TitleRecyclerItem(this.resources.getString(R.string.trending_cryptos)))
        viewModel.topTitle.postValue(TitleRecyclerItem(this.resources.getString(R.string.top_fifty)))
        binding.contractsTrendsRecycler.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        }
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
                    is Item -> {
                        navigateToDetailsActivty(
                            data.getItAsCryptoItem(),
                            title,
                            subTitle!!,
                            circleImageView
                        )
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