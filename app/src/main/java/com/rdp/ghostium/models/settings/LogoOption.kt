package com.rdp.ghostium.models.settings

import com.rdp.ghostium.abstraction.LocalModel

data class LogoOption(val appName: String = "Ghostzilla", val versionName: String) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }

}