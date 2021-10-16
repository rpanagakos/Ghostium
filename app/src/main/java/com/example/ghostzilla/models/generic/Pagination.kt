package com.example.ghostzilla.models.generic


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("page_number")
    val pageNumber: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("total_count")
    val totalCount: Int
)