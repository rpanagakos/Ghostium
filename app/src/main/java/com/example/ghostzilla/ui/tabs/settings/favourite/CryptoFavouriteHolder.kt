package com.example.ghostzilla.ui.tabs.settings.favourite

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.models.CryptoItemDB

class CryptoFavouriteHolder(
    val binding: ViewDataBinding,
    val listener: FavouriteClickListener? = null,
    val viewModel: FavouriteViewModel? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun present(data: LocalModel, position: Int) {
        when (data) {
            is CryptoItemDB -> {
                binding.setVariable(BR.crypto, data)
                binding.setVariable(BR.viewModel, viewModel)
                binding.setVariable(BR.clickHandler, listener)
                binding.setVariable(BR.dataPosition, position)
            }
        }
    }
}