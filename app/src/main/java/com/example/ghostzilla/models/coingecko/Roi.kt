package com.example.ghostzilla.models.coingecko


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Roi(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("percentage")
    val percentage: Double,
    @SerializedName("times")
    val times: Double
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}