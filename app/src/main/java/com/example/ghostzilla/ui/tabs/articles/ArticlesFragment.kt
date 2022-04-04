package com.example.ghostzilla.ui.tabs.articles

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentArticlesBinding
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import com.example.ghostzilla.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment :
    AbstractFragment<FragmentArticlesBinding, ArticlesViewModel>(R.layout.fragment_articles) {

    @Inject
    lateinit var dataStore: DataStoreUtil

    override val viewModel: ArticlesViewModel by viewModels()

    override fun initLayout() {
        binding.articlesRecyclerView.addOnScrollListener(object :
            BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        viewModel.runOperation() { data: LocalModel ->
            when (data) {
                is CryptoShimmer -> makeCall()
                is Article -> (requireActivity() as TabsActivity).openBottomsheetOptions(data)
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