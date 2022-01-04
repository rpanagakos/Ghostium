package com.example.ghostzilla.ui.tabs.trends

import android.content.Intent
import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.ui.tabs.TabsBinding.loadImageFromUrl
import com.example.ghostzilla.ui.tabs.TabsBinding.removeHtmlText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    private var cryptoItem: CryptoItem? = null
    private val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {
        val intent: Intent = intent
        cryptoItem = intent.getParcelableExtra<CryptoItem?>("coin")
        coinImageDetails.loadImageFromUrl(cryptoItem?.image)
        coinNameDetail.text = cryptoItem?.name
        coinTickerSymbolDetail.text = cryptoItem?.symbol
        viewModel.searchCoin(cryptoItem?.id ?: "")
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    override fun runOperation() {
        descriptionText.removeHtmlText("")
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun stopOperation() {
    }

}