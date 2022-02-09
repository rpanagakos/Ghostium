package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

data class LogoOption(val appName : String = "Ghostzilla"): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }

}