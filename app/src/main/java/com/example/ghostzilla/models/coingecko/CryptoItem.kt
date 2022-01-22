package com.example.ghostzilla.models.coingecko


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cryptos_table")
data class CryptoItem(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerializedName("current_price")
    var currentPrice: Double,
    @SerializedName("image")
    val image: String,
    @SerializedName("low_24h")
    val low24h: Double? = null,
    @SerializedName("market_cap")
    val marketCap: Long? = null,
    @SerializedName("max_supply")
    val maxSupply: Double? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Double? = null,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("symbol")
    val symbol: String
) : LocalModel, Parcelable {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is CryptoItem -> name == obj.name && currentPrice == obj.currentPrice && symbol == obj.symbol
        else -> false
    }
}