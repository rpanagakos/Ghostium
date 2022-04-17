package com.rdp.ghostium.ui.tabs.cryptos.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractAdapter
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.databinding.HolderCoinItemBinding
import com.rdp.ghostium.databinding.HolderEmptyBinding
import com.rdp.ghostium.models.coingecko.tredings.Coin
import java.util.concurrent.atomic.AtomicInteger

class TrendingCryptosAdapter(
    private val listener: ItemOnClickListener? = null
) : AbstractAdapter() {

    val currentPosition: AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_coin_item -> {
                val view = HolderCoinItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TrendingCryptoViewHolder(view, listener)
            }
            else -> {
                val view = HolderEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TrendingCryptoViewHolder(view, listener)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentPosition.set(position)
        return when (holder) {
            is TrendingCryptoViewHolder -> {
                holder.present(getItem(position))
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is Coin -> R.layout.holder_coin_item
        else -> R.layout.holder_empty
    }

}