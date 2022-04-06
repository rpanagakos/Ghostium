package com.example.ghostzilla.ui.tabs.articles

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentArticlesBinding
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import com.example.ghostzilla.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment :
    AbstractFragment<FragmentArticlesBinding, ArticlesViewModel>(R.layout.fragment_articles) {

    override val viewModel: ArticlesViewModel by viewModels()

    override fun initLayout() {
        binding.articlesRecyclerView.addOnScrollListener(object :
            BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        viewModel.runOperation() { data: LocalModel, title: TextView?, subTitle: TextView?, imageView: ImageView? ->
            when (data) {
                is CryptoShimmer -> makeCall()
                is Article ->
                {
                    if (title == null)
                        (requireActivity() as TabsActivity).openBottomsheetOptions(data)
                    else if (subTitle != null && imageView != null)
                        navigateToArticlesActivty(data, title, subTitle, imageView)
                }
            }
        }
        if (viewModel.articlesPagingAdapter.itemCount == 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val list =
                    listOf(CryptoShimmer(), CryptoShimmer(), CryptoShimmer(), CryptoShimmer(), CryptoShimmer())
                viewModel.articlesPagingAdapter.submitData(PagingData.from(list))
            }
        }
    }

    private fun makeCall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getArticlesList(TitleRecyclerItem(this@ArticlesFragment.resources.getString(R.string.news_in_brief))).observe(viewLifecycleOwner, {
                viewModel.articlesPagingAdapter.submitData(lifecycle, it as PagingData<LocalModel>)
            })
        }
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}
}