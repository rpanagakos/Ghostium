package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

class CurrencyItem (
    val nameCur : String,
    var isSeleted : Boolean = false
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}