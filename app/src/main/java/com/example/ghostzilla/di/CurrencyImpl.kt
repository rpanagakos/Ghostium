package com.example.ghostzilla.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ghostzilla.R
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.*
import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyImpl @Inject constructor(
    @ApplicationContext context: Context
) {
    private lateinit var currency: String
    lateinit var currencySymbol : String

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

    fun updateChosenCurrency(){
        if (this::currency.isInitialized && currency != getCurrencyFromShared())
        {
            currency = getCurrency()
        }
    }

    fun getCurrency(): String {
        if (!this::currency.isInitialized || currency != getCurrencyFromShared()) {
            currency = getCurrencyFromShared()
            getCurrencySymbol()
        }
        return currency
    }

    fun getCurrencyImg() : Int {
        return when (getCurrency()) {
            CurrencyItem.CurrencyID.EURO.value -> R.drawable.ic_euro
            CurrencyItem.CurrencyID.DOLLAR.value, CurrencyItem.CurrencyID.ADOLLAR.value -> R.drawable.ic_dollar
            CurrencyItem.CurrencyID.POUNDS.value -> R.drawable.ic_sterling_sign_light
            else -> R.drawable.ic_euro
        }
    }

    private fun getCurrencySymbol(){
        val currencyLoc = Currency.getInstance(currency)
        currencySymbol = currencyLoc.symbol
    }
    //Numberfortmatter.getInstance

}