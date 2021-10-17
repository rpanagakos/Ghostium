package com.example.ghostzilla.ui.tabs

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ghostzilla.R
import de.hdodenhof.circleimageview.CircleImageView


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
}