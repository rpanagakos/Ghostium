package com.rdp.ghostium.ui.tabs.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractAdapter
import com.rdp.ghostium.abstraction.listeners.GeneralClickListener
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.databinding.*
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.search.CoinResult
import com.rdp.ghostium.models.coingecko.shimmer.CoinsSearchShimmer
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.opensea.Asset
import com.rdp.ghostium.models.settings.AppOption
import com.rdp.ghostium.models.settings.LogoOption
import com.rdp.ghostium.models.settings.RecentlyItem
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import java.util.concurrent.atomic.AtomicInteger

class TabsAdapter(
    private val listener: ItemOnClickListener? = null,
    private val currencyImpl: CurrencySource? = null,
    private val generalClickListener: GeneralClickListener? = null
) : AbstractAdapter() {

    val currentPosition: AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_trends_item -> {
                val view = HolderTrendsItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener, currencyImpl)
            }
            R.layout.holder_nft_item -> {
                val view = HolderNftItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_options_item -> {
                val view = HolderOptionsItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_logo_item -> {
                val view = HolderLogoItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_recently_item -> {
                val view = HolderRecentlyItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, generalClickListener = generalClickListener)
            }
            R.layout.holder_title_item -> {
                val view = HolderTitleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_trending_coins_items -> {
                val view = HolderTrendingCoinsItemsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_searched_item -> {
                val view = HolderSearchedItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
            R.layout.holder_shimmer_cryptos -> {
                val view = HolderShimmerCryptosBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShimmerViewHolder(view)
            }
            R.layout.holder_shimmer_searched -> {
                val view = HolderShimmerSearchedBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShimmerViewHolder(view)
            }
            else -> {
                val view = HolderEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentPosition.set(position)
        return when (holder) {
            is TabsViewHolder -> {
                holder.present(getItem(position), position == itemCount - 1)
            }
            is ShimmerViewHolder -> holder.present(getItem(position), false)
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is CryptoItem -> R.layout.holder_trends_item
        is Asset -> R.layout.holder_nft_item
        is AppOption -> R.layout.holder_options_item
        is LogoOption -> R.layout.holder_logo_item
        is RecentlyItem -> R.layout.holder_recently_item
        is TitleRecyclerItem -> R.layout.holder_title_item
        is TredingCoins -> R.layout.holder_trending_coins_items
        is CryptoShimmer -> R.layout.holder_shimmer_cryptos
        is CoinsSearchShimmer -> R.layout.holder_shimmer_searched
        is CoinResult -> R.layout.holder_searched_item
        else -> R.layout.holder_empty
    }

}