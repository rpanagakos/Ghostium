package com.example.ghostzilla.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.*

class LangContextWrapper private constructor(base: Context) : ContextWrapper(base) {

    companion object {

        private val enLocale = Locale("en")
        private val deLocale = Locale("de")

        fun wrap(baseContext: Context, language: String): ContextWrapper {
            var wrappedContext = baseContext
            val config = Configuration(baseContext.resources.configuration)

            if (language.isNotBlank()) {
                val locale = returnOrCreateLocale(language)
                Locale.setDefault(locale)
                config.setLocale(locale)
                config.setLayoutDirection(locale)
                wrappedContext = baseContext.createConfigurationContext(config)
            }
            return LangContextWrapper(wrappedContext)
        }

        /**
         * Method to return the existing local instead of creating new Locale object every time
         * For now it is only for EN ot AR locale it will return the static objects
         * @param language - the requested locale lang
         * @return the created Locale (or the static variables in case of AR or EN)
         */
        private fun returnOrCreateLocale(language: String): Locale {
            return when (language) {
                "en" -> enLocale
                "de" -> deLocale
                else -> Locale(language)
            }
        }
    }
}