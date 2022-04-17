package com.rdp.ghostium.ui.tabs.cryptos.recycler

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.rdp.ghostium.abstraction.AbstractBindingViewHolder
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.models.coingecko.tredings.Coin

class TrendingCryptoViewHolder(
    val binding: ViewDataBinding,
    val listener: ItemOnClickListener? = null
) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel, isLastElement: Boolean) {
        when (data) {
            is Coin -> {
                binding.setVariable(BR.coin, data.item)
                binding.setVariable(BR.clickHandler, listener)
            }
        }
    }
}