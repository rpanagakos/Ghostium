package com.example.ghostzilla.models.guardian


import com.google.gson.annotations.SerializedName

data class GuardianResponse(
    @SerializedName("response")
    val response: Response
)