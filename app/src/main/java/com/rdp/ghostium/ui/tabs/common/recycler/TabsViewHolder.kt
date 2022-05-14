package com.rdp.ghostium.ui.tabs.common.recycler

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.rdp.ghostium.abstraction.AbstractBindingViewHolder
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.GeneralClickListener
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.search.CoinResult
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.opensea.Asset
import com.rdp.ghostium.models.settings.AppOption
import com.rdp.ghostium.models.settings.LogoOption
import com.rdp.ghostium.models.settings.RecentlyItem
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.ui.tabs.cryptos.recycler.TrendingCryptosAdapter

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
            is LogoOption -> {
                binding.setVariable(BR.item, data)
            }
            is CoinResult -> {
                binding.setVariable(BR.crypto, data)
                binding.setVariable(BR.clickHandler, listener)
            }
        }
    }
}