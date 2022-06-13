package com.rdp.ghostium.ui.tabs.cryptos

import android.view.View
import androidx.activity.viewModels
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractActivity
import com.rdp.ghostium.databinding.ActivityDetailsBinding
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.SparkLineStyle
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.github.mikephil.charting.data.LineData
import com.google.android.material.tabs.TabLayout
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertLongToDate
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertPrice
import com.rdp.ghostium.utils.resetChart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    @Inject
    lateinit var sparkLineStyle: SparkLineStyle

    @Inject
    lateinit var currencyImpl: CurrencySource

    private var cryptoItem: CryptoItem? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        viewModel.isLoading.value = true
        cryptoItem = intent.getParcelableExtra("coin")
        viewModel.cryptoItem = CryptoItemDB(
            cryptoItem!!.id, image = cryptoItem!!.image, name = cryptoItem!!.name,
            symbol = cryptoItem!!.symbol, currentPrice = cryptoItem!!.currentPrice
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
                    viewModel.selectedTab(tab.text.toString(), cryptoItem!!)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.price.observe(this){
            binding.priceIndicator.convertPrice(it, currencyImpl)
            dateIndicator.convertLongToDate(System.currentTimeMillis(), resources.configuration.locale)
            sparkLineStyle.styleChart(
                binding.chartLine, System.currentTimeMillis(), it,
                binding.priceIndicator, binding.dateIndicator, binding.scrollView, currencyImpl
            )
        }
        viewModel.lineDataSet.observe(this) {
            binding.chartLine.resetChart()
            sparkLineStyle.styleLineDataSet(it)
            binding.chartLine.data = LineData(it)
            binding.chartLine.invalidate()
        }
        viewModel.isLoading.observe(this) {
            if (!it) {
                binding.layoutShimmer.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        }
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun stopOperation() {}

}