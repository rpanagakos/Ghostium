package com.example.ghostzilla.ui.tabs.articles.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.databinding.HolderArticleItemBinding
import com.example.ghostzilla.models.guardian.Article

class ArticlesViewHolder(private val binding: HolderArticleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun present(item: Article) {
        with(binding) {
            article = item
        }
    }
}