package com.rdp.ghostium.models.settings

import com.rdp.ghostium.abstraction.LocalModel

data class TitleRecyclerItem(val titleRecycler: String) : LocalModel {

    override fun equalsContent(obj: LocalModel): Boolean {
        return when(obj){
            is TitleRecyclerItem -> obj.titleRecycler == titleRecycler
            else -> false
        }
    }
}