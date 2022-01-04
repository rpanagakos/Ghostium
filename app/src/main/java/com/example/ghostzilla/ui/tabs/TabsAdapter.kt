package com.example.ghostzilla.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.DiffUtilClass
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderTrendsItemBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import java.util.concurrent.atomic.AtomicInteger

class TabsAdapter(private val listener: ItemOnClickListener) : AbstractAdapter(){

    val currentPosition : AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_trends_item -> {
                val view = HolderTrendsItemBinding.inflate(
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
        else -> R.layout.holder_empty
    }

}