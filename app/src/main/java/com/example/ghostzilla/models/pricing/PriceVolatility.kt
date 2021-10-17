package com.example.ghostzilla.models.pricing


import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.generic.Pagination
import com.google.gson.annotations.SerializedName

data class PriceVolatility(
    @SerializedName("items")
    val items: List<Crypto>,
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("updated_at")
    val updatedAt: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}