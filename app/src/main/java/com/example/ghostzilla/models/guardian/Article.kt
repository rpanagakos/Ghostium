package com.example.ghostzilla.models.guardian


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles_table")
data class Article(
    @SerializedName("apiUrl")
    val apiUrl: String,
    @SerializedName("fields")
    val fields: Fields,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerializedName("isHosted")
    val isHosted: Boolean,
    @SerializedName("pillarId")
    val pillarId: String,
    @SerializedName("pillarName")
    val pillarName: String,
    @SerializedName("sectionId")
    val sectionId: String,
    @SerializedName("sectionName")
    val sectionName: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("webPublicationDate")
    val webPublicationDate: String,
    @SerializedName("webTitle")
    val webTitle: String,
    @SerializedName("webUrl")
    val webUrl: String,
    @SerializedName("shortUrl")
    val shortUrl: String
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return when (obj) {
            is Article -> obj.id == id && obj.fields == obj.fields
            else -> false
        }
    }

}