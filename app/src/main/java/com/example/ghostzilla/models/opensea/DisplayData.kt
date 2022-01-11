package com.example.ghostzilla.models.opensea


import com.google.gson.annotations.SerializedName

data class DisplayData(
    @SerializedName("card_display_style")
    val cardDisplayStyle: String
)