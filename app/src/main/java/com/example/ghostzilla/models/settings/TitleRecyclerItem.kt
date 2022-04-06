package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

data class TitleRecyclerItem(val titleRecycler: String) : LocalModel {

    override fun equalsContent(obj: LocalModel): Boolean {
        return when(obj){
            is TitleRecyclerItem -> obj.titleRecycler == titleRecycler
            else -> false
        }
    }
}