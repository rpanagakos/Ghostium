package com.example.ghostzilla.ui.tabs.search

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentSearchBinding
import com.example.ghostzilla.di.CurrencyImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment :
    AbstractFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var currencyImpl: CurrencyImpl

    override fun initLayout() {
        viewModel.runOperation()
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}