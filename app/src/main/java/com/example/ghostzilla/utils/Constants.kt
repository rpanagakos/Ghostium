package com.example.ghostzilla.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class Constants {

    companion object {
        const val COIN_GECKO_BASE_URL = "https://api.coingecko.com/"
        const val OPEN_SEA_BASE_URL = "https://api.opensea.io/"
        const val GUARDIAN_BASE_URL = "https://content.guardianapis.com/"
        const val GUARDIAN_FIELDS = "headline,trailText,byline,thumbnail"
        const val GUARDIAN_CONTENT = "cryptos"

        //Room database
        const val GHOSTZILLA_NAME = "ghostzilla_database"
        const val CRYPTOS_TABLE = ""

        const val LOTTIE_FULL_STATE = 1f
        const val LOTTIE_STARTING_STATE = 0f

        const val SHARED_PREF = "ghostzilla"
    }

    object DataStore {
        val DATA = booleanPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }
}