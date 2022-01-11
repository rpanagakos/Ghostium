package com.example.ghostzilla.models.opensea


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String
)