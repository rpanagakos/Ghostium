package com.example.ghostzilla.models.account

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.ghostzilla.abstraction.LocalModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionItem(
    @DrawableRes
    val image: Int,
    val title : String): LocalModel, Parcelable {
    override fun equalsContent(obj: LocalModel): Boolean = true
}