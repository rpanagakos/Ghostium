package com.example.ghostzilla.ui.tabs.common

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.opensea.Asset
import com.example.ghostzilla.models.settings.AppOption

class TabsViewHolder(val binding: ViewDataBinding, val listener: ItemOnClickListener? = null, val currencyImpl: CurrencyImpl? = null) :
    AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when (data) {
            is CryptoItem -> {
                binding.setVariable(BR.crypto, data)
                binding.setVariable(BR.clickHandler, listener)
                binding.setVariable(BR.currencyIml, currencyImpl)
            }
            is Asset -> {
                binding.setVariable(BR.nft, data)
            }
            is AppOption -> {
                binding.setVariable(BR.option, data)
                binding.setVariable(BR.clickHandler, listener)
            }
        }
    }
}