package com.example.ghostzilla.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ghostzilla.models.settings.CurrencyItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyImpl @Inject constructor(
    @ApplicationContext context: Context
) {
    private var currencySymbol = "€"

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "Currency",
        Context.MODE_PRIVATE
    )

    fun saveCurrency(currencyItem: CurrencyItem) {
        changeCurrencySymbol(currencyItem.currencyID)
        preferences.edit().putString("Currency", currencyItem.currencyID.value).apply()
    }

    fun getCurrency(): String {
        return preferences.getString("Currency", "eur") ?: "eur"
    }

    private fun changeCurrencySymbol(currencyId: CurrencyItem.CurrencyID) {
        currencySymbol = when (currencyId) {
            CurrencyItem.CurrencyID.EURO -> "€"
            CurrencyItem.CurrencyID.ADOLLAR, CurrencyItem.CurrencyID.DOLLAR -> "$"
            CurrencyItem.CurrencyID.POUNDS -> "£"
        }
    }

    fun getCurrencySymbol() = currencySymbol
}