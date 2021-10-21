package com.example.ghostzilla.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.DiffUtilClass
import com.example.ghostzilla.abstraction.EmptyHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.HolderTrendsItemBinding
import com.example.ghostzilla.models.pricing.Crypto

class TabsAdapter(private val onClickElement: (selected: Int) -> Unit) :
    ListAdapter<LocalModel, RecyclerView.ViewHolder>(DiffUtilClass<LocalModel>()) {

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
            else -> EmptyHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is TabsViewHolder -> {
            onClickElement.invoke(position)
            holder.present(getItem(position))
        }
        else -> Unit
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is Crypto -> R.layout.holder_trends_item
        else -> R.layout.holder_empty
    }
}