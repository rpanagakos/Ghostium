package com.rdp.ghostium.ui.tabs.articles.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.rdp.ghostium.databinding.HolderLoaderItemBinding

class LoaderStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.present(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = HolderLoaderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LoaderViewHolder(view)
    }

}