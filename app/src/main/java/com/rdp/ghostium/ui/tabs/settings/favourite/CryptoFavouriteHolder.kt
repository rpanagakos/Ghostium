package com.rdp.ghostium.ui.tabs.settings.favourite

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.FavouriteClickListener
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.models.CryptoItemDB

class CryptoFavouriteHolder(
    val binding: ViewDataBinding,
    val listener: FavouriteClickListener? = null,
    val viewModel: FavouriteViewModel? = null,
    val currencyImpl: CurrencySource? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun present(data: LocalModel, position: Int) {
        when (data) {
            is CryptoItemDB -> {
                binding.setVariable(BR.crypto, data)
                binding.setVariable(BR.viewModel, viewModel)
                binding.setVariable(BR.clickHandler, listener)
                binding.setVariable(BR.currencyIml, currencyImpl)
                binding.setVariable(BR.dataPosition, position)
            }
        }
    }
}