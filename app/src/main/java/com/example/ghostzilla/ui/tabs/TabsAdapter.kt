package com.example.ghostzilla.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderTrendsItemBinding
import com.example.ghostzilla.models.coingecko.MarketsItem

class TabsAdapter(private val onClickElement: (selected: Int) -> Unit) : AbstractAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_trends_item -> {
                val view = HolderTrendsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TabsViewHolder(view)
            }
            else -> {
                val view = HolderEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TabsViewHolder(view)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is TabsViewHolder -> {
            holder.present(getItem(position))
            onClickElement.invoke(position)
        }
        else -> Unit
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MarketsItem -> R.layout.holder_trends_item
        else -> R.layout.holder_empty
    }

}