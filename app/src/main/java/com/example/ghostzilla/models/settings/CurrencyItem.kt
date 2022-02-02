package com.example.ghostzilla.models.settings

import com.example.ghostzilla.abstraction.LocalModel

class CurrencyItem (
    val currencyID : CurrencyID,
    val nameCur : String,
    var isSeleted : Boolean = false
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false

    enum class CurrencyID(val value : String) {
        EURO("eur"),
        DOLLAR("dollar"),
        POUNDS("pounds"),
        ADOLLAR("adollar")
    }
}