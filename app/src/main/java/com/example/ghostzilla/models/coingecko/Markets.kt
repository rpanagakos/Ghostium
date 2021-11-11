package com.example.ghostzilla.models.coingecko

import com.example.ghostzilla.abstraction.LocalModel

data class Markets(val marketsList: ArrayList<MarketsItem>): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}