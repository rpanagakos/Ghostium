package com.rdp.ghostium.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rdp.ghostium.abstraction.LocalModel
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
    var isSelected: Boolean = false
) : LocalModel, Parcelable {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is CryptoItemDB -> name == obj.name && currentPrice == obj.currentPrice && symbol == obj.symbol && isSelected == obj.isSelected
        else -> false
    }
}