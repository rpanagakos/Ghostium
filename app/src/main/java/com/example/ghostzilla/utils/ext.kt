package com.example.ghostzilla.utils

import android.app.Activity
import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import androidx.core.view.postDelayed
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.showKeyboard() {
    postDelayed(100) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun EditText.clearTextAndFocus(context: Context) {
    this.apply {
        setText("")
        clearFocus()
        context.hideKeyboard(this)
    }
}

fun ImageView.changeImageOnEdittext(editText: EditText, emptyImage: Int, nonEmpty: Int) {
    if (editText.text.toString().isEmpty())
        this.setImageResource(emptyImage)
    else
        this.setImageResource(nonEmpty)
}

fun String.removeWhiteSpaces(): String {
    return this.replace("\\s".toRegex(), "")
}

@ExperimentalCoroutinesApi
fun EditText.searchQuery() = callbackFlow<Unit> {
    doAfterTextChanged { offer(Unit) }
    awaitClose()
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun getMyLongValue(vararg any: Any): Float {
    return when (val tmp = any.first()) {
        is Number -> tmp.toFloat()
        else -> throw Exception("not a number") // or do something else reasonable for your case
    }
}

fun TextView.setTextViewLinkHtml(html: String, linkClickCallBack: ((Int, String) -> Unit)? = null) {
    val newHtml = html.replace("\n", "<br>")
    val sequence = Html.fromHtml(newHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans<URLSpan>(0, sequence.length)

    urls.forEach {
        if (linkClickCallBack != null) {
            strBuilder.makeLinkClickable(it, urls.indexOf(it), linkClickCallBack)
        }
    }

    text = strBuilder
    linksClickable = true
    movementMethod = LinkMovementMethod.getInstance()
}

private fun SpannableStringBuilder.makeLinkClickable(
    span: URLSpan,
    index: Int,
    linkClickCallBack: ((Int, String) -> Unit)? = null
) {
    val start = getSpanStart(span)

    val end =
        getSpanEnd(span)

    val flags = getSpanFlags(span)
    val clickable = object : ClickableSpan() {
        override fun onClick(widget: View) {
            linkClickCallBack?.invoke(index, span.url)
        }
    }
    setSpan(clickable, start, end, flags)
    removeSpan(span)
}
