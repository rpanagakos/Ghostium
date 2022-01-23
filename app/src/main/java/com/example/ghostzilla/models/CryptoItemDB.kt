package com.example.ghostzilla.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.CryptoItem
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cryptos_table")
data class CryptoItemDB(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var currentPrice: Double,
    val image: String,
    val low24h: Double? = null,
    val name: String,
    val symbol: String,
    val isSelected: Boolean = false
) : LocalModel, Parcelable {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is CryptoItem -> name == obj.name && currentPrice == obj.currentPrice && symbol == obj.symbol
        else -> false
    }
}