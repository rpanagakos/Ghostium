package com.rdp.ghostium.ui.tabs.articles.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractPagingAdapter
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.databinding.HolderArticleItemBinding
import com.rdp.ghostium.databinding.HolderEmptyBinding
import com.rdp.ghostium.databinding.HolderShimmerArticleBinding
import com.rdp.ghostium.databinding.HolderTitleItemBinding
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.ui.tabs.common.recycler.ShimmerViewHolder
import com.rdp.ghostium.ui.tabs.common.recycler.TabsViewHolder

class ArticlesAdapter(private val listener: ItemOnClickListener,
                      private val generalAction: (data: LocalModel) -> Unit) : AbstractPagingAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_article_item -> {
                val view = HolderArticleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ArticlesViewHolder(view, listener)
            }
            R.layout.holder_shimmer_article -> {
                val view = HolderShimmerArticleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShimmerViewHolder(view)
            }
            R.layout.holder_title_item -> {
                val view = HolderTitleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view)
            }
            else -> {
                val view = HolderEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TabsViewHolder(view)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is ArticlesViewHolder -> {
                holder.present(getItem(position = position) as Article, generalAction)
            }
            is ShimmerViewHolder -> {
                if (position == 1)
                    generalAction.invoke(CryptoShimmer())
                getItem(position)?.let { holder.present(it, false) } ?: Unit
            }
            is TabsViewHolder -> {
                getItem(position)?.let { holder.present(it, position == itemCount - 1) } ?: Unit
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position = position)) {
        is Article -> R.layout.holder_article_item
        is CryptoShimmer -> R.layout.holder_shimmer_article
        is TitleRecyclerItem -> R.layout.holder_title_item
        else -> R.layout.holder_empty
    }

}