package com.rdp.ghostium.models.coingecko.shimmer

import com.rdp.ghostium.abstraction.LocalModel

class CryptoShimmer() : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return false
    }
}