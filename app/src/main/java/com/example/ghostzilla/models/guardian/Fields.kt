package com.example.ghostzilla.models.guardian


import com.google.gson.annotations.SerializedName

data class Fields(
    @SerializedName("byline")
    val byline: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("trailText")
    val trailText: String
)