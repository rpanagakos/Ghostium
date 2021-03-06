package com.rdp.ghostium.utils

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Currency
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.format.DateUtils
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.postDelayed
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.github.mikephil.charting.charts.LineChart
import com.rdp.ghostium.ui.tabs.common.WebviewActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.*


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
    doAfterTextChanged { trySend(Unit).isSuccess }
    awaitClose()
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun convertMonthsToDays(tabSelected: String): Int {
    when {
        tabSelected.contains("d") -> {
            return (tabSelected.subSequence(0, tabSelected.indexOf('d'))).toString().toInt()
        }
        tabSelected.contains("m") -> {
            val months = (tabSelected.subSequence(0, tabSelected.indexOf('m'))).toString().toInt()
            return (months * 30)
        }
        tabSelected.contains("y") -> {
            val months = (tabSelected.subSequence(0, tabSelected.indexOf('y'))).toString().toInt()
            return (months * 365)
        }
    }
    return 1
}

fun View.fadeIn(durationMillis: Long = 250) {
    this.startAnimation(AlphaAnimation(0F, 1F).apply {
        duration = durationMillis
        fillAfter = true
    })
}

fun View.fadeOut(durationMillis: Long = 250) {
    this.startAnimation(AlphaAnimation(1F, 0F).apply {
        duration = durationMillis
        fillAfter = true
    })
}

fun View.dummyFadeOut(durationMillis: Long = 50) {
    this.startAnimation(AlphaAnimation(0F, 0F).apply {
        duration = durationMillis
        fillAfter = true
    })
}

fun LineChart.resetChart() {
    this.fitScreen()
    this.data?.clearValues()
    this.xAxis.valueFormatter = null
    this.notifyDataSetChanged()
    this.clear()
    this.invalidate()
}

fun getMyLongValue(vararg any: Any): Float {
    return when (val tmp = any.first()) {
        is Number -> tmp.toFloat()
        else -> throw Exception("not a number") // or do something else reasonable for your case
    }
}

fun View.convertToSpannableStringBuilder(text : String, imageGetter: ImageGetter?) : SpannableStringBuilder{
    val newHtml = text.replace("\n", "<br>")
    val sequence = Html.fromHtml(newHtml, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
    return SpannableStringBuilder(sequence)
}

fun TextView.setTextViewLinkHtml(
    strBuilder: SpannableStringBuilder,
    linkClickCallBack: ((String) -> Unit)? = null
) {
    text = strBuilder
    linksClickable = true
    movementMethod = object : CustomLinkMovementMethod() {
        override fun onLinkClicked(url: String) {
            when (url) {
                "showMore" -> {
                    linkClickCallBack?.invoke(url)
                }
                else -> {
                    val intent =
                        Intent(this@setTextViewLinkHtml.context, WebviewActivity::class.java)
                    intent.putExtra("url", url)
                    this@setTextViewLinkHtml.context.startActivity(intent)
                }
            }
        }
    }
}

fun SpannableString.getSpannableTextForPrices(fractionalDigits: Int = 2, context: Context): SpannableString {
    var fractDigits = fractionalDigits + 1
    if (context.resources.configuration.locale !=  Locale.ENGLISH)
        fractDigits++
    val end = this.length - fractDigits
    this.setSpan(
        RelativeSizeSpan(1.1f),
        0,
        if (end >= 0) end else 0,
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return this
}

fun getSpannableText(span: SpannableString, originalText: String): SpannableString {
    when {
        originalText.contains(".") -> {
            val index = originalText.indexOf(".")
            span.setSpan(
                RelativeSizeSpan(1.1f),
                0,
                index,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        originalText.contains(",") -> {
            val index = originalText.indexOf(",")
            span.setSpan(
                RelativeSizeSpan(1.1f),
                0,
                index,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        !originalText.isNullOrEmpty() -> {
            span.setSpan(
                RelativeSizeSpan(1.1f),
                0,
                originalText.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
    return span
}

fun View.disable() {
    this.apply {
        isClickable = false
        isEnabled = false
    }
}

fun View.enable() {
    this.apply {
        isClickable = true
        isEnabled = true
    }
}

fun LottieAnimationView.enableAfterAnimation() {
    this.playAnimation()
    this.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            this@enableAfterAnimation.enable()
        }

        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
    })
}

fun View.appearWithCustomAnimation(animationFile: Int, context: Context) {
    this.apply {
        startAnimation(AnimationUtils.loadAnimation(context, animationFile))
        show()
    }
}

fun View.disappearWithCustomAnimation(animationFile: Int, context: Context) {
    this.apply {
        hide()
        startAnimation(AnimationUtils.loadAnimation(context, animationFile))
    }
}

fun Double.format(currency: Currency, fractionalDigits: Int = 2): String =
    NumberFormat.getCurrencyInstance()
        .apply {
            minimumFractionDigits = fractionalDigits
            maximumFractionDigits = fractionalDigits
            setCurrency(currency)
        }.format(this)

fun String.convertDateToAgoString(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = inputFormat.parse(this)
        val niceDateStr = DateUtils.getRelativeTimeSpanString(
            date.time,
            Calendar.getInstance().getTimeInMillis(),
            DateUtils.MINUTE_IN_MILLIS
        )
        niceDateStr.toString()
    } catch (e: Exception) {
        this
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
