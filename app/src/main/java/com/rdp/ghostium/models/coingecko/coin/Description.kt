package com.rdp.ghostium.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    val en: String
)