package com.rdp.ghostium.ui.tabs.articles

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentArticlesBinding
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.ui.tabs.articles.recycler.LoaderStateAdapter
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment :
    AbstractFragment<FragmentArticlesBinding, ArticlesViewModel>(R.layout.fragment_articles) {

    override val viewModel: ArticlesViewModel by viewModels()

    override fun initLayout() {
        binding.articlesRecyclerView.apply {
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
            adapter = viewModel.articlesPagingAdapter.withLoadStateFooter( LoaderStateAdapter { viewModel.articlesPagingAdapter.retry()})
        }
        viewModel.runOperation { data: LocalModel, title: TextView?, subTitle: TextView?, imageView: ImageView? ->
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
                    listOf(TitleRecyclerItem(requireContext().resources.getString(R.string.news_in_brief)),CryptoShimmer(), CryptoShimmer(), CryptoShimmer(), CryptoShimmer(), CryptoShimmer())
                viewModel.articlesPagingAdapter.submitData(PagingData.from(list))
            }
        }

        onBackPressedWithoutPop {
            when (binding.articlesRecyclerView.layoutManager?.findViewByPosition(0)?.isVisible) {
                true -> findNavController().popBackStack()
                else -> viewModel.scrollToTopRecycler(binding.articlesRecyclerView)
            }
        }
    }

    private fun makeCall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getArticlesList(TitleRecyclerItem(this@ArticlesFragment.resources.getString(R.string.news_in_brief))).observe(viewLifecycleOwner) {
                viewModel.articlesPagingAdapter.submitData(lifecycle, it as PagingData<LocalModel>)
            }
        }
    }

    override fun observeViewModel() {
        viewModel.isConnected.observe(this) {
            if (it && containsShimmerData())
                viewModel.articlesPagingAdapter.retry()
        }
    }

    override fun stopOperations() {}

    private fun containsShimmerData() : Boolean {
        return if (viewModel.articlesPagingAdapter.itemCount > 0)
            viewModel.articlesPagingAdapter.getItemViewType(1) == R.layout.holder_shimmer_article
        else
            false
    }
}