package com.example.ghostzilla.ui.tabs.trends

import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.di.SparkLineStyle
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.utils.customview.CustomMarker
import com.example.ghostzilla.utils.getMyLongValue
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    @Inject
    lateinit var sparkLineStyle: SparkLineStyle

    private var cryptoItem: CryptoItem? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        cryptoItem = intent.getParcelableExtra("coin")
        when (cryptoItem) {
            null -> onBackPressed()
            else -> {
                binding.viewModel = viewModel
                binding.crypto = cryptoItem
                viewModel.runOperation(cryptoItem!!.id)
            }
        }
        sparkLineStyle.styleChart(binding.chartLine, binding.priceIndicator, binding.dateIndicator)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.lineDataSet.observe(this, {
            sparkLineStyle.styleLineDataSet(it)
            binding.chartLine.data = LineData(it)
            binding.chartLine.invalidate()
        })
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun stopOperation() {}

}