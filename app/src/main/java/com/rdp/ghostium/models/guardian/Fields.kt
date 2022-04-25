package com.rdp.ghostium.models.guardian

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fields(
    @SerializedName("byline")
    private val _byline: String? = "",
    @SerializedName("headline")
    private val _headline: String? = "",
    @SerializedName("thumbnail")
    private val _thumbnail: String? = "",
    @SerializedName("trailText")
    private val _trailText: String? = "",
    @SerializedName("body")
    private val _body: String? = "",
    @SerializedName("main")
    private val _main: String? = ""
) : Parcelable {

    val thumbnail
        get() = _thumbnail ?: ""

    val byline
        get() = _byline ?: ""

    val headline
        get() = _headline ?: ""

    val trailText
        get() = _trailText ?: ""

    val body
        get() = _body ?: ""

    val main
        get() = _main ?: ""

}