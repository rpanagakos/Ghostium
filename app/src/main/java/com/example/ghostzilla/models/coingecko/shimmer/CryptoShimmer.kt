package com.example.ghostzilla.models.coingecko.shimmer

import com.example.ghostzilla.abstraction.LocalModel

class CryptoShimmer() : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return false
    }
}