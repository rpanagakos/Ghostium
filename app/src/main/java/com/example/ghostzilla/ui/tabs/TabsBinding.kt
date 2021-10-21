package com.example.ghostzilla.ui.tabs

import android.annotation.SuppressLint
import android.widget.TextView
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
        val dec = DecimalFormat("#,###.####")
        val roundedPrice = dec.format(cryptoPrice)
        text = "$roundedPrice$"
    }
}