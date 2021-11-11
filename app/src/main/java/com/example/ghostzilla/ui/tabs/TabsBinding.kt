package com.example.ghostzilla.ui.tabs

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import androidx.core.text.set
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ghostzilla.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat

@SuppressLint("SetTextI18n")
object TabsBinding {

    @BindingAdapter(value = ["imageURLContract"])
    @JvmStatic
    fun CircleImageView.loadImageFromUrl(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .error(Glide.with(this).load(R.drawable.ic_launcher_foreground))
                .into(this)
        }
    }

    @BindingAdapter("cryptoPrice")
    @JvmStatic
    fun TextView.convertPrice(cryptoPrice: Float) {
        var dec = DecimalFormat("#,###.####")
        var roundedPrice = dec.format(cryptoPrice)
        if (roundedPrice.equals("0"))
        {
            dec = DecimalFormat("#,###.######")
            roundedPrice = dec.format(cryptoPrice)
        }
        val spannableInt = SpannableString(roundedPrice + " €")
        if (roundedPrice.contains(".")){
            val index = roundedPrice.indexOf(".")
            spannableInt.setSpan(
                RelativeSizeSpan(1.1f),
                0,
                index,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }else {
            spannableInt.setSpan(
                RelativeSizeSpan(1.1f),
                0,
                roundedPrice.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }

        text = spannableInt
    }
}