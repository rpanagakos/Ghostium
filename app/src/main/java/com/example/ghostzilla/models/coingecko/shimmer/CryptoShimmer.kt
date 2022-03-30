package com.example.ghostzilla.models.coingecko.shimmer

import com.example.ghostzilla.abstraction.LocalModel

data class CryptoShimmer( val id : Int = 0
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return false
    }
}