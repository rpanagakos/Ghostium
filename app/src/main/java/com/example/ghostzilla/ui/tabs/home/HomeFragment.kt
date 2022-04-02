package com.example.ghostzilla.ui.tabs.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : AbstractFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var dataStore: DataStoreUtil
    
    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() {
        viewModel.runOperation() { data: LocalModel -> }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCryptoList().observe(viewLifecycleOwner, {
                viewModel.articlesPagingAdapter.submitData(lifecycle, it as PagingData<LocalModel>)
            })
        }
    }

    override fun stopOperations() {}
}