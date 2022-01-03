package com.example.ghostzilla.abstraction

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import androidx.databinding.library.baseAdapters.BR

abstract class AbstractFragment<T : ViewDataBinding, VM : ViewModel>(contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    lateinit var binding: T
    abstract val viewModel : VM

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()
        binding.setVariable(BR.viewModel, viewModel )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    abstract fun initLayout()

    abstract fun observeViewModel()

    override fun onPause() {
        stopOperations()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    abstract fun stopOperations()
}