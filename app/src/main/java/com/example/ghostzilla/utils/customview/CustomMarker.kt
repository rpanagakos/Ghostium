package com.example.ghostzilla.utils.customview

import android.content.Context
import android.widget.TextView
import com.example.ghostzilla.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF




class CustomMarker(context: Context) : MarkerView(context, R.layout.custom_marker) {

    override fun refreshContent(entry: Entry, highlight: Highlight) {
        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF? {
        return MPPointF(
            (-(width / 2)).toFloat(),
            (-height / 2).toFloat()
        ) // place the midpoint of marker over the bar
    }
}