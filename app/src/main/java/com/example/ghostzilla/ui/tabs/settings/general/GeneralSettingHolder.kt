package com.example.ghostzilla.ui.tabs.settings.general

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.models.settings.LanguageItem

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