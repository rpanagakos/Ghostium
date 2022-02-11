package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

class RecentlyItem(val searchedText : String): LocalModel {

    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }
}