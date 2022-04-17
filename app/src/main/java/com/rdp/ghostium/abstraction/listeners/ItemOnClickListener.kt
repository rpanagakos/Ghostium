package com.rdp.ghostium.abstraction.listeners

import android.widget.ImageView
import android.widget.TextView
import com.rdp.ghostium.abstraction.LocalModel

interface ItemOnClickListener {

    fun onClick(data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: ImageView)
}