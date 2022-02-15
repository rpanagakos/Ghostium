package com.example.ghostzilla.ui.tabs.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentSearchBinding
import com.example.ghostzilla.utils.changeImageOnEdittext
import com.example.ghostzilla.utils.removeWhiteSpaces
import com.example.ghostzilla.utils.searchQuery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment :
    AbstractFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    override fun initLayout() {
        viewModel.runOperation()
        binding.searchLayout.searchEditText.apply {
            searchQuery()
                .debounce(350)
                .onEach {
                    binding.searchLayout.searchButton.changeImageOnEdittext(
                        binding.searchLayout.searchEditText,
                        R.drawable.ic_search,
                        R.drawable.ic_outline_clear
                    )
                    if (!this.text.isNullOrEmpty())
                        viewModel.searchCoin(this.text.toString().lowercase().removeWhiteSpaces())
                    else
                        viewModel.runOperation()
                }
                .launchIn(lifecycleScope)
        }
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }

}