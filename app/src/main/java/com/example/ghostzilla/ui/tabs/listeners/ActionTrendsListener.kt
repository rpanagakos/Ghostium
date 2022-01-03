package com.example.ghostzilla.ui.tabs.listeners

import android.widget.TextView
import com.example.ghostzilla.abstraction.LocalModel
import de.hdodenhof.circleimageview.CircleImageView

interface ActionTrendsListener {

    fun onClickDetails(
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    )
}