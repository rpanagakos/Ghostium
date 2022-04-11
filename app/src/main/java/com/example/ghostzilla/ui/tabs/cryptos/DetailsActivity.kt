package com.example.ghostzilla.ui.tabs.cryptos

import android.view.View
import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.di.common.CurrencyImpl
import com.example.ghostzilla.di.common.SparkLineStyle
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.utils.resetChart
import com.github.mikephil.charting.data.LineData
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity :
    AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    @Inject
    lateinit var sparkLineStyle: SparkLineStyle

    @Inject
    lateinit var currencyImpl: CurrencyImpl

    private var cryptoItem: CryptoItem? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        viewModel.isLoading.value = true
        cryptoItem = intent.getParcelableExtra("coin")
        viewModel.cryptoItem = CryptoItemDB(
            cryptoItem!!.id,
            image = cryptoItem!!.image,
            name = cryptoItem!!.name,
            symbol = cryptoItem!!.symbol,
            currentPrice = cryptoItem!!.currentPrice
        )
        binding.currencyIml = currencyImpl
        when (cryptoItem) {
            null -> onBackPressed()
            else -> {
                binding.viewModel = viewModel
                binding.crypto = cryptoItem
                viewModel.runOperation(cryptoItem!!.id)
            }
        }

        binding.daysTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewModel.selectedTab(
                        binding.priceIndicator, binding.dateIndicator,
                        tab.text.toString(), cryptoItem!!
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        sparkLineStyle.styleChart(
            binding.chartLine,
            binding.priceIndicator,
            binding.dateIndicator,
            binding.scrollView,
            currencyImpl
        )
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.lineDataSet.observe(this, {
            binding.chartLine.resetChart()
            sparkLineStyle.styleLineDataSet(it)
            binding.chartLine.data = LineData(it)
            binding.chartLine.invalidate()
        })
        viewModel.isLoading.observe(this,{
            if (!it){
                binding.layoutShimmer.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        })
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun stopOperation() {}

}