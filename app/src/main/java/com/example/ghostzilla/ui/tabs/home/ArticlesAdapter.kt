package com.example.ghostzilla.ui.tabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R
import com.example.ghostzilla.databinding.DummyHolderBinding
import com.example.ghostzilla.databinding.HolderArticleItemBinding
import com.example.ghostzilla.models.guardian.Result

class ArticlesAdapter (private val cryptoClickListener: (article: com.example.ghostzilla.models.guardian.Result) -> Unit) :
    PagingDataAdapter<Result, ArticlesAdapter.ViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<HolderArticleItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.holder_article_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: HolderArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        init {
            binding.root.setOnClickListener {
                val article = getItem(absoluteAdapterPosition)
                article?.let { cryptoClickListener(it) }
                binding.articleImage.setClipToOutline(true)
            }
        }

        fun bind(item: Result) {
            with(binding) {
                article = item
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}