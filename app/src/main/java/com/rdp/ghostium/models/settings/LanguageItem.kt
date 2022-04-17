package com.rdp.ghostium.models.settings

import com.rdp.ghostium.abstraction.LocalModel

class LanguageItem(
    val id : String,
    val nameLang : String,
    var isSeleted : Boolean = false
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}