package com.rdp.ghostium.ui.tabs.articles.recycler

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.databinding.HolderLoaderItemBinding
import com.rdp.ghostium.utils.setSafeOnClickListener

class LoaderViewHolder(private val binding: HolderLoaderItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun present(loadState: LoadState, retry: () -> Unit) {
        binding.isLoading = loadState is LoadState.Loading
        binding.retryButton.setSafeOnClickListener { retry.invoke() }
    }
}