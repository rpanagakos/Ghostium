package com.rdp.ghostium.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Price24h(
    @SerializedName("aud")
    val aud: Double= 0.0,
    @SerializedName("eur")
    val eur: Double= 0.0,
    @SerializedName("gbp")
    val gbp: Double= 0.0,
    @SerializedName("usd")
    val usd: Double= 0.0
)