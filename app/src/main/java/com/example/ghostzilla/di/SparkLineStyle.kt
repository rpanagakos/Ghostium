package com.example.ghostzilla.di

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.ghostzilla.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SparkLineStyle @Inject constructor(
    @ApplicationContext val context: Context)
{

    fun styleChart(lineChart: LineChart) = lineChart.apply {
        // (1)
        axisLeft.isEnabled = false
        axisRight.isEnabled = false
        xAxis.isEnabled = false
        legend.isEnabled = false
        description.isEnabled = false

        // (2)
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)
    }

    fun styleLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.white)
        valueTextColor = ContextCompat.getColor(context, R.color.white)
        setDrawValues(false)
        lineWidth = 1.5f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

    }
}
