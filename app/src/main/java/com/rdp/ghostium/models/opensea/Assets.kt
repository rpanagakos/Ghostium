package com.rdp.ghostium.models.opensea


import com.google.gson.annotations.SerializedName

data class Assets(
    @SerializedName("assets")
    val assets: List<Asset>
)