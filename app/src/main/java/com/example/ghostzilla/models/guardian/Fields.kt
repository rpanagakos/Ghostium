package com.example.ghostzilla.models.guardian

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fields(
    @SerializedName("byline")
    val byline: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("trailText")
    val trailText: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("main")
    val main: String
) : Parcelable