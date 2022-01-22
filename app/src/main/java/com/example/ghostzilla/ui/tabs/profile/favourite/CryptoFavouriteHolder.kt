package com.example.ghostzilla.ui.tabs.profile.favourite

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.CryptoItem

class CryptoFavouriteHolder(val binding: ViewDataBinding) :
    AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when (data) {
            is CryptoItem -> {
                binding.setVariable(BR.crypto, data)
            }
        }
    }
}