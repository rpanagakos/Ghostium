package com.rdp.ghostium.utils.customview

import android.content.Context
import android.widget.TextView
import com.rdp.ghostium.R
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertLongToDate
import com.rdp.ghostium.ui.tabs.common.TabsBinding.convertPrice
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF


class CustomMarker(context: Context, var price : TextView, var date: TextView, val currencyImpl: CurrencyImpl) : MarkerView(context, R.layout.custom_marker) {

    override fun refreshContent(entry: Entry, highlight: Highlight) {
        super.refreshContent(entry, highlight)
        price.convertPrice(entry.y.toDouble(), currencyImpl)
        date.convertLongToDate(entry.x.toLong(), context.resources.configuration.locale)
    }

    override fun getOffset(): MPPointF? {
        return MPPointF(
            (-(width / 2)).toFloat(),
            (-height / 2).toFloat()
        ) // place the midpoint of marker over the bar
    }
}