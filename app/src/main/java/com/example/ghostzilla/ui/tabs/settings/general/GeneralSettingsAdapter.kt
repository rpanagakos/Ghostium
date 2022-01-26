package com.example.ghostzilla.ui.tabs.settings.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractAdapter
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.abstraction.listeners.GeneralClickListener
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderFavouriteItemBinding
import com.example.ghostzilla.databinding.HolderGeneralItemBinding
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.settings.LanguageItem
import com.example.ghostzilla.ui.tabs.settings.favourite.CryptoFavouriteHolder
import com.example.ghostzilla.ui.tabs.settings.favourite.FavouriteViewModel
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
                holder.present(getItem(position), position = position)
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is LanguageItem -> R.layout.holder_general_item
        else -> R.layout.holder_empty
    }

}