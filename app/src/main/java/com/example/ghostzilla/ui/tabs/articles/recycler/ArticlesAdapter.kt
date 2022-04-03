package com.example.ghostzilla.ui.tabs.articles.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractPagingAdapter
import com.example.ghostzilla.databinding.HolderArticleItemBinding
import com.example.ghostzilla.databinding.HolderEmptyBinding
import com.example.ghostzilla.databinding.HolderShimmerArticleBinding
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.ui.tabs.common.ShimmerViewHolder
import com.example.ghostzilla.ui.tabs.common.TabsViewHolder

class ArticlesAdapter(private val filledWithShimmer: () -> Unit) : AbstractPagingAdapter(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_article_item -> {
                val view = HolderArticleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ArticlesViewHolder(view)
            }
            R.layout.holder_shimmer_article -> {
                val view = HolderShimmerArticleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShimmerViewHolder(view)
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
                holder.present(getItem(position = position) as Article)
            }
            is ShimmerViewHolder -> {
                if (position == 1)
                    filledWithShimmer.invoke()
                getItem(position)?.let { holder.present(it, false) } ?: Unit
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position = position)) {
        is Article -> R.layout.holder_article_item
        is CryptoShimmer -> R.layout.holder_shimmer_article
        else -> R.layout.holder_empty
    }

}