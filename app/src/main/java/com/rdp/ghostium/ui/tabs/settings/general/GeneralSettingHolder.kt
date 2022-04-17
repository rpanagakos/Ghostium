package com.rdp.ghostium.ui.tabs.settings.general

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.GeneralClickListener
import com.rdp.ghostium.models.settings.CurrencyItem
import com.rdp.ghostium.models.settings.LanguageItem

class GeneralSettingHolder(
    val binding: ViewDataBinding,
    val listener: GeneralClickListener? = null,
    val viewModel: GeneralSettingsViewModel? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun present(data: LocalModel, position: Int, isLastElement: Boolean) {
        when (data) {
            is LanguageItem, is CurrencyItem -> {
                binding.setVariable(BR.item, data)
                binding.setVariable(BR.dataPosition, position)
                binding.setVariable(BR.clickHandler, listener)
                binding.setVariable(BR.isLastOption, isLastElement)
            }
        }
    }
}