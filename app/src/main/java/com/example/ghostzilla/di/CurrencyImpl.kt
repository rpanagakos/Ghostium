package com.example.ghostzilla.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyImpl @Inject constructor(
    @ApplicationContext context: Context
) {
    private lateinit var currency: String

    private val preferences: SharedPreferences = context.getSharedPreferences(
        Constants.SHARED_PREF,
        Context.MODE_PRIVATE
    )

    fun saveCurrency(currencyItem: CurrencyItem) {
        currency = currencyItem.currencyID.value
        preferences.edit().putString("Currency", currencyItem.currencyID.value).apply()
    }

    private fun getCurrencyFromShared(): String {
        return preferences.getString("Currency", "eur") ?: "eur"
    }

    /*private fun changeCurrencySymbol(currencyId: CurrencyItem.CurrencyID) {
        currencySymbol = when (currencyId) {
            CurrencyItem.CurrencyID.EURO -> "€"
            CurrencyItem.CurrencyID.ADOLLAR, CurrencyItem.CurrencyID.DOLLAR -> "$"
            CurrencyItem.CurrencyID.POUNDS -> "£"
        }
    }*/

    fun updateChosenCurrency(){
        if (this::currency.isInitialized && currency != getCurrencyFromShared())
        {
            currency = getCurrency()
        }
    }

    fun getCurrency(): String {
        if (!this::currency.isInitialized || currency != getCurrencyFromShared())
            currency = getCurrencyFromShared()
        return currency
    }

    private fun getSymbol(currency: String): String {
        return when (currency) {
            CurrencyItem.CurrencyID.EURO.value -> "€"
            CurrencyItem.CurrencyID.DOLLAR.value, CurrencyItem.CurrencyID.ADOLLAR.value -> "$"
            else -> "£"
        }
    }
}