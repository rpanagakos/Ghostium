package com.rdp.ghostium.abstraction.listeners

import android.widget.TextView
import com.rdp.ghostium.abstraction.LocalModel
import de.hdodenhof.circleimageview.CircleImageView

interface FavouriteClickListener {

    fun onClick(data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: CircleImageView, position : Int)
}