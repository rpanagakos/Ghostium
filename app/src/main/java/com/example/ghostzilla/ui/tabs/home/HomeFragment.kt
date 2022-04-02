package com.example.ghostzilla.ui.tabs.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    AbstractFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var dataStore: DataStoreUtil

    private val cryptoListAdapter by lazy {
        ArticlesAdapter {
            Toast.makeText(requireContext(), "fefefe", Toast.LENGTH_SHORT).show()
        }
    }


    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() {
        binding.recyclerview.adapter = cryptoListAdapter

        viewModel.runOperation() { data: LocalModel -> }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCryptoList().observe(viewLifecycleOwner, {
                cryptoListAdapter.submitData(lifecycle, it)
            })
        }
    }

    override fun stopOperations() {}
}