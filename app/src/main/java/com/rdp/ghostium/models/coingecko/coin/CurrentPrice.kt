package com.rdp.ghostium.models.coingecko.coin


import com.rdp.ghostium.models.settings.CurrencyItem
import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("aud")
    private val aud: Double?,
    @SerializedName("eur")
    private val eur: Double?,
    @SerializedName("gbp")
    private val gbp: Double?,
    @SerializedName("usd")
    private val usd: Double?
) {

    fun getPrice(currency: String): Double {
        when (currency) {
            CurrencyItem.CurrencyID.EURO.value -> return eur ?: 0.0
            CurrencyItem.CurrencyID.DOLLAR.value -> return usd ?: 0.0
            CurrencyItem.CurrencyID.ADOLLAR.value -> return aud ?: 0.0
            else -> return gbp ?: 0.0
        }
    }
}