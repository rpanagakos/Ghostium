package com.example.ghostzilla.ui.tabs.trends

import android.content.Intent
import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.ui.tabs.TabsBinding.loadImageFromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    private var cryptoItem: CryptoItem? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        binding.viewModel = viewModel
        val intent: Intent = intent
        cryptoItem = intent.getParcelableExtra<CryptoItem?>("coin")
        coinImageDetails.loadImageFromUrl(cryptoItem?.image)
        coinNameDetail.text = cryptoItem?.name
        coinTickerSymbolDetail.text = cryptoItem?.symbol
        viewModel.searchCoin(cryptoItem?.id ?: "")
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun stopOperation() {}

}