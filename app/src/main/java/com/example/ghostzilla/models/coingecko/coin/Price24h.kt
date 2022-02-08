package com.example.ghostzilla.models.coingecko.coin


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Price24h(
    @SerializedName("aud")
    val aed: Double= 0.0,
    @SerializedName("eur")
    val eur: Double= 0.0,
    @SerializedName("gbp")
    val gbp: Double= 0.0,
    @SerializedName("usd")
    val usd: Double= 0.0
) : LocalModel{
    override fun equalsContent(obj: LocalModel): Boolean {
        return false
    }
}