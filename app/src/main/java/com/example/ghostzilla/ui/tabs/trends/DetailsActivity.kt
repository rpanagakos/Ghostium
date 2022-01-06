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
        sparkLineStyle.styleChart(binding.chartLine)
        observeViewModel()
    }

    private fun observeViewModel() {
        val entries = mutableListOf<Entry>()
        viewModel._chartData.observe(this, {
            it.prices.forEach { item ->
                val x = getMyLongValue(item[0])
                val y = getMyLongValue(item[1])
                entries.add(Entry(x, y))
            }
            val dataSet = LineDataSet(entries, "Price Range")
            binding.chartLine.marker = CustomMarker(context = this)
            sparkLineStyle.styleLineDataSet(dataSet)
            binding.chartLine.data = LineData(dataSet)
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