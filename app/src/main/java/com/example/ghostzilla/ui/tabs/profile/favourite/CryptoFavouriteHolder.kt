package com.example.ghostzilla.ui.tabs.profile.favourite

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.models.CryptoItemDB

class CryptoFavouriteHolder(
    val binding: ViewDataBinding,
    val listener: FavouriteClickListener? = null
) :
    AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when (data) {
            is CryptoItemDB -> {
                binding.setVariable(BR.crypto, data)
                binding.setVariable(BR.clickHandler, listener)
            }
        }
    }
}