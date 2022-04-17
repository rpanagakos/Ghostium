package com.rdp.ghostium.models.settings

import com.rdp.ghostium.abstraction.LocalModel

class CurrencyItem (
    val currencyID : CurrencyID,
    val nameCur : String,
    var isSeleted : Boolean = false
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false

    enum class CurrencyID(val value : String) {
        EURO("eur"),
        DOLLAR("usd"),
        POUNDS("gbp"),
        ADOLLAR("aud")
    }
}