package com.example.ghostzilla.ui.tabs.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentHomeBinding
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : AbstractFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var dataStore: DataStoreUtil
    
    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() {
        binding.articlesRecyclerView.addOnScrollListener(object :
            BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        viewModel.runOperation() { data: LocalModel ->
            makeCall()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val list = listOf(CryptoShimmer(),CryptoShimmer(),CryptoShimmer(),CryptoShimmer(),CryptoShimmer() )
            viewModel.articlesPagingAdapter.submitData( PagingData.from(list))
        }
    }

    private fun makeCall(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getArticlesList().observe(viewLifecycleOwner, {
                viewModel.articlesPagingAdapter.submitData(lifecycle, it as PagingData<LocalModel>)
            })
        }
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}
}