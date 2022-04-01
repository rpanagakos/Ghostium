package com.example.ghostzilla.di.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ghostzilla.R
import com.example.ghostzilla.utils.customview.CustomScrollView
import com.example.ghostzilla.utils.customview.CustomMarker
import com.example.ghostzilla.utils.fadeIn
import com.example.ghostzilla.utils.fadeOut
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SparkLineStyle @Inject constructor(
    @ApplicationContext val context: Context) {

    @SuppressLint("ClickableViewAccessibility")
    fun styleChart(
        lineChart: LineChart, priceIndicator: TextView, dateIndicator: TextView, scrollView: CustomScrollView, currencyImpl: CurrencyImpl
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
                    priceIndicator.fadeIn()
                    dateIndicator.fadeIn()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_SCROLL -> {
                    priceIndicator.fadeOut(600)
                    dateIndicator.fadeOut(600)
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
