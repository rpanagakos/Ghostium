package com.example.ghostzilla.utils

import androidx.datastore.preferences.core.stringPreferencesKey

class ConstantApi {

    companion object {
        const val COIN_GECKO_BASE_URL = "https://api.coingecko.com/"
        const val OPEN_SEA_BASE_URL = "https://api.opensea.io/"

        //Room database
        const val DATABASE_NAME = ""
        const val LOCATIONS_TABLE = ""
    }

    object DataStore {
        val DATA = stringPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }
}