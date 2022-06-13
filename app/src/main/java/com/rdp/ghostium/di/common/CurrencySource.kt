package com.rdp.ghostium.di.common

import com.rdp.ghostium.models.settings.CurrencyItem

interface CurrencySource {
    fun saveCurrency(currencyItem: CurrencyItem)
    fun updateChosenCurrency()
    fun getCurrency(): String
}