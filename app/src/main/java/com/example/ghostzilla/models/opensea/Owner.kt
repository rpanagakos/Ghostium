package com.example.ghostzilla.models.opensea


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("address")
    val address: String,
    @SerializedName("config")
    val config: String,
    @SerializedName("profile_img_url")
    val profileImgUrl: String,
    @SerializedName("user")
    val user: UserX
)