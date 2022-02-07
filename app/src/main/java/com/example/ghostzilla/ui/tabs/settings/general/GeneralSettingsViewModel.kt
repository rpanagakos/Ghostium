package com.example.ghostzilla.ui.tabs.settings.general

import android.app.Application
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.models.settings.LanguageItem
import com.example.ghostzilla.utils.LangContextWrapper
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val currencyImpl: CurrencyImpl,
    application: Application
) : AbstractViewModel(application), GeneralClickListener {

    val generalAdapter = GeneralSettingsAdapter(this, this)
    val langChanged = SingleLiveEvent<Boolean>()
    private val langList = listOf(
        LanguageItem("en", context.getString(R.string.english), false),
        LanguageItem("de", context.getString(R.string.deutsch), false),
        LanguageItem("it", context.getString(R.string.italiano), false),
        LanguageItem("es", context.getString(R.string.espanol), false),
    )

    private val currencyList = listOf(
        CurrencyItem(
            CurrencyItem.CurrencyID.EURO,
            context.getString(R.string.euro_currency),
            false
        ),
        CurrencyItem(
            CurrencyItem.CurrencyID.DOLLAR,
            context.getString(R.string.dollar_currency),
            false
        ),
        CurrencyItem(
            CurrencyItem.CurrencyID.ADOLLAR,
            context.getString(R.string.australian_currency),
            false
        ),
        CurrencyItem(
            CurrencyItem.CurrencyID.POUNDS,
            context.getString(R.string.pounds_currency),
            false
        )
    )

    fun runOperation(isLangFragment: Boolean) {
        if (isLangFragment) {
            langList.forEach { lang ->
                if (lang.id == LangContextWrapper.getSavedLang(context))
                    lang.isSeleted = true
            }
            generalAdapter.submitList(langList)
        } else {
            currencyList.forEach { currencyItem ->
                if (currencyItem.currencyID.value == currencyImpl.getCurrency())
                    currencyItem.isSeleted = true
            }
            generalAdapter.submitList(currencyList)
        }
    }

    private fun changeCurrentLang(languageItem: LanguageItem, position: Int) {
        langList.forEach {
            it.isSeleted = it == languageItem
        }
        LangContextWrapper.setAppLocale(context, languageItem.id)
        generalAdapter.notifyDataSetChanged()
        langChanged.postValue(true)
    }

    private fun changeCurrentCurrency(currencyItem: CurrencyItem, position: Int) {
        currencyList.forEach {
            it.isSeleted = it == currencyItem
        }
        currencyImpl.saveCurrency(currencyItem)
        generalAdapter.notifyDataSetChanged()
    }

    override fun onClick(data: LocalModel, position: Int) {
        when (data) {
            is LanguageItem -> changeCurrentLang(data, position)
            is CurrencyItem -> changeCurrentCurrency(data, position)
        }
    }


}
