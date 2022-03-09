package com.example.ghostzilla.ui.tabs.settings.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.databinding.HolderCurrencyItemBinding
import com.example.ghostzilla.databinding.HolderGeneralItemBinding
import com.example.ghostzilla.models.settings.CurrencyItem
import com.example.ghostzilla.models.settings.LanguageItem
import java.util.concurrent.atomic.AtomicInteger

class GeneralSettingsAdapter(
    private val viewModel: GeneralSettingsViewModel,
    private val listener: GeneralClickListener
) : AbstractAdapter() {

    private val currentPosition: AtomicInteger = AtomicInteger(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_general_item -> {
                val view = HolderGeneralItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GeneralSettingHolder(view, listener, viewModel)
            }
            R.layout.holder_currency_item -> {
                val view = HolderCurrencyItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GeneralSettingHolder(view, listener, viewModel)
            }
            else -> {
                val view = HolderGeneralItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GeneralSettingHolder(view, listener)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentPosition.set(position)
        return when (holder) {
            is GeneralSettingHolder -> {
                holder.present(getItem(position), position = position, position == itemCount - 1)
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is LanguageItem -> R.layout.holder_general_item
        is CurrencyItem -> R.layout.holder_currency_item
        else -> R.layout.holder_empty
    }

}