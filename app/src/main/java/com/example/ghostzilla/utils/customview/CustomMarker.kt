package com.example.ghostzilla.utils.customview

import android.content.Context
import android.widget.TextView
import com.example.ghostzilla.R
import com.example.ghostzilla.ui.tabs.common.TabsBinding.convertLongToDate
import com.example.ghostzilla.ui.tabs.common.TabsBinding.convertPrice
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF


class CustomMarker(context: Context, var price : TextView, var date: TextView) : MarkerView(context, R.layout.custom_marker) {

    override fun refreshContent(entry: Entry, highlight: Highlight) {
        super.refreshContent(entry, highlight)
        price.convertPrice(entry.y.toDouble())
        date.convertLongToDate(entry.x.toLong())
    }

    override fun getOffset(): MPPointF? {
        return MPPointF(
            (-(width / 2)).toFloat(),
            (-height / 2).toFloat()
        ) // place the midpoint of marker over the bar
    }
}