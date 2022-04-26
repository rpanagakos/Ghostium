package com.rdp.ghostium.ui.tabs.search

import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentSearchBinding
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.utils.changeImageOnEdittext
import com.rdp.ghostium.utils.removeWhiteSpaces
import com.rdp.ghostium.utils.searchQuery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment :
    AbstractFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    override fun initLayout() {
        viewModel.searchTitle.postValue(TitleRecyclerItem(this.resources.getString(R.string.recently_searches)))
        viewModel.runOperation { data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: ImageView ->
            when (data) {
                is CryptoItem -> {
                    navigateToDetailsActivty(data, title, subTitle!!, circleImageView)
                }
            }
        }
        binding.searchLayout.searchEditText.apply {
            searchQuery()
                .debounce(400)
                .onEach {
                    binding.searchLayout.searchButton.changeImageOnEdittext(
                        binding.searchLayout.searchEditText,
                        R.drawable.ic_search,
                        R.drawable.ic_outline_clear
                    )
                    if (!this.text.isNullOrEmpty() && !checkIfContainsInvalidString())
                        viewModel.searchCoin(this.text.toString().lowercase().removeWhiteSpaces())
                    else if (binding.searchLayout.searchEditText.hasFocus()) {
                        binding.generalRecycler.removeAllViewsInLayout()
                        viewModel.clearSearch()
                    }
                }
                .launchIn(lifecycleScope)
            this.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (!checkIfContainsInvalidString())
                        viewModel.searchCoin(this.text.toString().lowercase().removeWhiteSpaces())
                }
                true
            }
        }
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}

    private fun checkIfContainsInvalidString() : Boolean{
        binding.searchLayout.searchEditText.text?.let {
            if ((it.length == 1 && it.contains(".")) || (it.length == 2 && it.contains("..")))
                return true
        }
        return false
    }
}