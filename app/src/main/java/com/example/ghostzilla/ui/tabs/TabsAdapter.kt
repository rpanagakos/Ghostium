package com.example.ghostzilla.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.DiffUtilClass
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderTrendsItemBinding

/*
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
            val data = getItem(position)
            when (data) {
                is Crypto -> {
                    checkFalseCrypto(data, holder, position)
                }
                else -> {
                }
            }

        }
        else -> Unit
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is Crypto -> R.layout.holder_trends_item
        else -> R.layout.holder_empty
    }


    private fun checkFalseCrypto(crypto: Crypto, holder: TabsViewHolder, position: Int) {
        if (crypto.contractName == null) {
            holder.itemView.layoutParams.width = 0
            holder.itemView.layoutParams.height = 0
        } else {
            onClickElement.invoke(position)
            holder.present(getItem(position))
        }
    }

}*/
