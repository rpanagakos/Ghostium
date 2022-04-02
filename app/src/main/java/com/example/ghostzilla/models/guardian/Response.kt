package com.example.ghostzilla.models.guardian


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("orderBy")
    val orderBy: String,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("results")
    val articles: List<Article>,
    @SerializedName("startIndex")
    val startIndex: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("userTier")
    val userTier: String
)