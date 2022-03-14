package com.example.ghostzilla.ui.tabs.common

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.opensea.Asset
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.models.settings.RecentlyItem
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.ui.tabs.cryptos.recycler.TrendingCryptosAdapter

class TabsViewHolder(
    val binding: ViewDataBinding,
    val listener: ItemOnClickListener? = null,
    val currencyImpl: CurrencyImpl? = null,
    val generalClickListener: GeneralClickListener? = null
) :
    AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel, isLastElement: Boolean) {
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
                binding.setVariable(BR.isLastOption, isLastElement)
            }
            is RecentlyItem -> {
                binding.setVariable(BR.recentlyItem, data)
                binding.setVariable(BR.onClickHandler, generalClickListener)
            }
            is TitleRecyclerItem -> {
                binding.setVariable(BR.item, data)
            }
            is TredingCoins -> {
                val adapter = TrendingCryptosAdapter(listener)
                binding.setVariable(BR.adapter, adapter)
                adapter.submitList(data.coins)
            }
        }
    }
}