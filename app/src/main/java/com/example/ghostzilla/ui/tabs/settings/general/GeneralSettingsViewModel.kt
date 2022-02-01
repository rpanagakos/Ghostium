package com.example.ghostzilla.ui.tabs.settings.general

import android.app.Application
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.models.settings.LanguageItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.LangContextWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application), GeneralClickListener {

    val generalAdapter = GeneralSettingsAdapter(this, this)
    private val langList = listOf(
        LanguageItem("en", context.getString(R.string.english), true),
        LanguageItem("de",context.getString(R.string.deutsch), false),
        LanguageItem("it",context.getString(R.string.italiano), false),
        LanguageItem("es",context.getString(R.string.espanol), false),
    )


    private val currencyList = listOf(
        CurrencyItem(context.getString(R.string.euro_currency), true),
        CurrencyItem(context.getString(R.string.dollar_currency), false),
        CurrencyItem(context.getString(R.string.australian_currency), false),
        CurrencyItem(context.getString(R.string.pounds_currency), false)
    )

    fun runOperation(isLangFragment : Boolean) {
        if (isLangFragment)
            generalAdapter.submitList(langList)
        else
            generalAdapter.submitList(currencyList)
    }

    private fun changeCurrentLang(languageItem: LanguageItem, position: Int) {
        langList.forEach {
            it.isSeleted = it == languageItem
        }
        LangContextWrapper.setAppLocale(context, languageItem.id)
        generalAdapter.notifyDataSetChanged()
    }

    private fun changeCurrentCurrency(currencyItem: CurrencyItem, position: Int) {
        currencyList.forEach {
            it.isSeleted = it == currencyItem
        }
        generalAdapter.notifyDataSetChanged()
    }

    override fun onClick(data: LocalModel, position: Int) {
        when (data) {
            is LanguageItem -> changeCurrentLang(data, position)
            is CurrencyItem -> changeCurrentCurrency(data, position )
        }
    }


}
