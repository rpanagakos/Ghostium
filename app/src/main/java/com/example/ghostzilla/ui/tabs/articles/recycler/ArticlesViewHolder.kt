package com.example.ghostzilla.ui.tabs.articles.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.databinding.HolderArticleItemBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.utils.setSafeOnClickListener

class ArticlesViewHolder(
    private val binding: HolderArticleItemBinding,
    private val itemListener: ItemOnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun present(item: Article, optionsClicked: (data: LocalModel) -> Unit) {
        with(binding) {
            article = item
            clickHandler = itemListener
            moreImage.setSafeOnClickListener {
                optionsClicked.invoke(item)
            }
        }
    }
}