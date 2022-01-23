package com.example.ghostzilla.ui.tabs.profile.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderFavouriteItemBinding
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.CryptoItem
import java.util.concurrent.atomic.AtomicInteger

class FavouriteAdapter(private val listener: FavouriteClickListener) : AbstractAdapter() {

    val currentPosition: AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_favourite_item -> {
                val view = HolderFavouriteItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CryptoFavouriteHolder(view, listener)
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
                holder.present(getItem(position))
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is CryptoItemDB -> R.layout.holder_favourite_item
        else -> R.layout.holder_empty
    }

}