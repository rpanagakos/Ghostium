package com.rdp.ghostium.di.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import com.rdp.ghostium.R
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertLongToDate
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertPrice
import com.rdp.ghostium.utils.customview.CustomMarker
import com.rdp.ghostium.utils.customview.CustomScrollView
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SparkLineStyle @Inject constructor(
    @ApplicationContext val context: Context) {

    @SuppressLint("ClickableViewAccessibility")
    fun styleChart(
        lineChart: LineChart, timetamps : Long, currenctPrice : Double, priceIndicator: TextView, dateIndicator: TextView, scrollView: CustomScrollView, currencyImpl: CurrencySource
    ) = lineChart.apply {
        axisLeft.isEnabled = false
        axisRight.isEnabled = false
        xAxis.isEnabled = false
        legend.isEnabled = false
        description.isEnabled = false
        marker = CustomMarker(context = context, priceIndicator, dateIndicator, currencyImpl)
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)

        setOnTouchListener { _, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollView.setScrolling(false)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_SCROLL -> {
                    lineChart.highlightValue(null)
                    priceIndicator.convertPrice(currenctPrice, currencyImpl)
                    dateIndicator.convertLongToDate(timetamps, context.resources.configuration.locale)
                    scrollView.setScrolling(true)
                }
            }
            false
        }
    }

    fun styleLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.white)
        valueTextColor = ContextCompat.getColor(context, R.color.white)
        lineWidth = 1.5f
        isHighlightEnabled = true
        setDrawCircles(false)
        setDrawHighlightIndicators(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
        setDrawValues(false)
    }
}
