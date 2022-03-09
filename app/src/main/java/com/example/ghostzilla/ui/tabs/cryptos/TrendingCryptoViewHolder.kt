package com.example.ghostzilla.ui.tabs.cryptos

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.models.coingecko.tredings.Coin

class TrendingCryptoViewHolder (
    val binding: ViewDataBinding,
    val listener: ItemOnClickListener? = null
) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when (data) {
            is Coin -> {
                binding.setVariable(BR.coin, data.item)
                binding.setVariable(BR.clickHandler, listener)
            }
        }
    }
}