package com.rdp.ghostium.models.settings

import androidx.annotation.DrawableRes
import com.rdp.ghostium.abstraction.LocalModel

data class AppOption(
    val title: String,
    @DrawableRes
    val imageID: Int,
    val type: SettingType
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false

    enum class SettingType {
        CRYPTO_FAV,
        NEWS_FAV,
        LANGUAGE,
        CURRENCY,
        SHARE_APP,
        CONTACT_US,
        RATE_APP,
        SAVE_ARTICLE,
        SHARE_ARTICLE
    }
}