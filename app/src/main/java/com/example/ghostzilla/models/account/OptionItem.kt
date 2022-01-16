package com.example.ghostzilla.models.account

import androidx.annotation.DrawableRes
import com.example.ghostzilla.abstraction.LocalModel

data class OptionItem(
    @DrawableRes
    val image: Int,
    val title : String): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = true
}