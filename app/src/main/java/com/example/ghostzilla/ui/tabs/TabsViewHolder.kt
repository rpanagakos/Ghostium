package com.example.ghostzilla.ui.tabs

import androidx.databinding.ViewDataBinding
import com.example.ghostzilla.BR
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.pricing.Crypto
import com.example.ghostzilla.models.pricing.PriceVolatility

class TabsViewHolder(val binding: ViewDataBinding) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel) {
        when(data){
            is Crypto -> {
                    binding.setVariable(BR.crypto, data)
            }
        }
    }
}