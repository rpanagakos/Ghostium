package com.rdp.ghostium.ui.tabs.settings.general

import android.app.Application
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.GeneralClickListener
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.models.settings.CurrencyItem
import com.rdp.ghostium.models.settings.LanguageItem
import com.rdp.ghostium.utils.LangContextWrapper
import com.rdp.ghostium.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val currencyImpl: CurrencySource,
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
                if (lang.id == LangContextWrapper.getSavedLang(context)) {
                    lang.isSeleted = true
                    return@forEach
                }
            }
            generalAdapter.submitList(langList)
        } else {
            currencyList.forEach { currencyItem ->
                if (currencyItem.currencyID.value == currencyImpl.getCurrency()) {
                    currencyItem.isSeleted = true
                    return@forEach
                }
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
