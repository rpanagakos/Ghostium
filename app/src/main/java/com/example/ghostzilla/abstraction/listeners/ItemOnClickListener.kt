package com.example.ghostzilla.abstraction.listeners

import android.widget.ImageView
import android.widget.TextView
import com.example.ghostzilla.abstraction.LocalModel
import de.hdodenhof.circleimageview.CircleImageView

interface ItemOnClickListener {

    fun onClick(data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: ImageView)
}