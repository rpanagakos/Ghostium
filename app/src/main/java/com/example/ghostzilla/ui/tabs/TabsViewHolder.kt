package com.example.ghostzilla.ui.tabs

import androidx.databinding.ViewDataBinding
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.MarketsItem

class TabsViewHolder(val binding: ViewDataBinding) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when(data){
            is MarketsItem -> {}
        }
    }
}