package com.example.ghostzilla.models.coingecko.coin


import com.example.ghostzilla.models.settings.CurrencyItem
import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("aud")
    private val aud: Double,
    @SerializedName("eur")
    private val eur: Double,
    @SerializedName("gbp")
    private val gbp: Double,
    @SerializedName("usd")
    private val usd: Double
) {

    fun getPrice(currency: String): Double {
        when (currency) {
            CurrencyItem.CurrencyID.EURO.value -> return eur
            CurrencyItem.CurrencyID.DOLLAR.value -> return usd
            CurrencyItem.CurrencyID.ADOLLAR.value -> return aud
            else -> return gbp
        }
    }
}