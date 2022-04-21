package com.rdp.ghostium.ui.tabs.settings.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractAdapter
import com.rdp.ghostium.abstraction.listeners.FavouriteClickListener
import com.rdp.ghostium.databinding.HolderEmptyBinding
import com.rdp.ghostium.databinding.HolderFavouriteItemBinding
import com.rdp.ghostium.databinding.HolderShimmerCryptosBinding
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.ui.tabs.common.recycler.ShimmerViewHolder
import java.util.concurrent.atomic.AtomicInteger

class FavouriteAdapter(
    private val viewModel: FavouriteViewModel,
    private val listener: FavouriteClickListener,
    private val currencyImpl: CurrencyImpl
) : AbstractAdapter() {

    private val currentPosition: AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_favourite_item -> {
                val view = HolderFavouriteItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CryptoFavouriteHolder(view, listener, viewModel, currencyImpl)
            }
            R.layout.holder_shimmer_cryptos -> {
                val view = HolderShimmerCryptosBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShimmerViewHolder(view)
            }
            else -> {
                val view = HolderEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CryptoFavouriteHolder(view, listener)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentPosition.set(position)
        return when (holder) {
            is CryptoFavouriteHolder -> {
                holder.present(getItem(position), position = position)
            }
            is ShimmerViewHolder -> holder.present(getItem(position), false)
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is CryptoItemDB -> R.layout.holder_favourite_item
        is CryptoShimmer -> R.layout.holder_shimmer_cryptos
        else -> R.layout.holder_empty
    }

}