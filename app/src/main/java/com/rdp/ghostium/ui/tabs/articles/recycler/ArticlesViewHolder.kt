package com.rdp.ghostium.ui.tabs.articles.recycler

import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.databinding.HolderArticleItemBinding
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.utils.setSafeOnClickListener

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