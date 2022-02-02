package com.example.ghostzilla.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

object LangContextWrapper {

    private const val LANGUAGE_KEY = "Language"
    private val enLocale = Locale("en")
    private val deLocale = Locale("de")
    private val esLocale = Locale("es")
    private val itLocale = Locale("it")

    fun wrap(baseContext: Context): ContextWrapper {
        val wrappedContext: Context
        val config = Configuration(baseContext.resources.configuration)

        val locale = returnOrCreateLocale(getSavedLang(baseContext))
        Locale.setDefault(locale)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        wrappedContext = baseContext.createConfigurationContext(config)

        return ContextWrapper(wrappedContext)
    }

    fun setAppLocale(context: Context, language: String) {
        saveSelectedLang(context, language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getSavedLang(context: Context): String {
        return context.getSharedPreferences(LANGUAGE_KEY, Context.MODE_PRIVATE)
            .getString(LANGUAGE_KEY, "en") ?: "en"
    }

    private fun saveSelectedLang(context: Context, language: String) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(LANGUAGE_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .putString(LANGUAGE_KEY, language)
            .apply()
    }

    private fun returnOrCreateLocale(language: String): Locale {
        return when (language) {
            "en" -> enLocale
            "de" -> deLocale
            "es" -> esLocale
            "it" -> itLocale
            else -> Locale(language)
        }
    }

}