package com.example.ghostzilla.ui.tabs.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.databinding.*
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.opensea.Asset
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.models.settings.LogoOption
import com.example.ghostzilla.models.settings.RecentlyItem
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import java.util.concurrent.atomic.AtomicInteger

class TabsAdapter(
    private val listener: ItemOnClickListener,
    private val currencyImpl: CurrencyImpl? = null
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
                TabsViewHolder(view, listener)
            }
            R.layout.holder_title_item -> {
                val view = HolderTitleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view, listener)
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
                holder.present(getItem(position))
            }
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
        else -> R.layout.holder_empty
    }

}