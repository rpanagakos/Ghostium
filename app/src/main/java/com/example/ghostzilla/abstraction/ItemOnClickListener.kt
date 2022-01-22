package com.example.ghostzilla.abstraction

import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

interface ItemOnClickListener {

    fun onClick(data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: CircleImageView)
}