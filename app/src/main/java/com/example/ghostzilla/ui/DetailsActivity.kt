package com.example.ghostzilla.ui

import android.content.Intent
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.ui.tabs.TabsBinding.loadImageFromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    private var marketItem: MarketsItem? = null

    override fun initLayout() {
        val intent: Intent = intent
        marketItem = intent.getParcelableExtra<MarketsItem?>("coin")
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    override fun runOperation() {
        coinImageDetails.loadImageFromUrl(marketItem?.image)
        coinNameDetail.text = marketItem?.name
        coinTickerSumbolDetail.text = marketItem?.symbol
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun stopOperation() {
    }

}