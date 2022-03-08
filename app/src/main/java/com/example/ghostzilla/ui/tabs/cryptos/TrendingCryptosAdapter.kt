package com.example.ghostzilla.ui.tabs.cryptos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.databinding.HolderCoinItemBinding
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.models.coingecko.tredings.Coin
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