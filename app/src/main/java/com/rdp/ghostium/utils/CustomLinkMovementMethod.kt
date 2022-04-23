package com.rdp.ghostium.utils

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.MotionEvent
import android.widget.TextView

abstract class CustomLinkMovementMethod : LinkMovementMethod() {

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action
        if (action != MotionEvent.ACTION_UP)
            return super.onTouchEvent(widget, buffer, event)
        val x = event.x - widget.totalPaddingLeft + widget.scrollX
        val y = event.y - widget.totalPaddingTop + widget.scrollY
        val layout = widget.layout
        val line = layout.getLineForVertical(y.toInt())
        val off = layout.getOffsetForHorizontal(line, x)
        val urlSpans: Array<URLSpan> = buffer.getSpans(off, off, URLSpan::class.java)
        if (urlSpans.isNotEmpty()) {
            onLinkClicked(urlSpans[0].url)
        }
        return true
    }

    abstract fun onLinkClicked(url : String)
}