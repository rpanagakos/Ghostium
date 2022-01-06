package com.example.ghostzilla.di

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.ghostzilla.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SparkLineStyle @Inject constructor(@ApplicationContext val context: Context) {

    fun styleChart(lineChart: LineChart) = lineChart.apply {
        axisLeft.isEnabled = false
        axisRight.isEnabled = false
        xAxis.isEnabled = false
        legend.isEnabled = false
        description.isEnabled = false

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)
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
