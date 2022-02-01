package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

class LanguageItem(
    val id : String,
    val nameLang : String,
    var isSeleted : Boolean = false
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}