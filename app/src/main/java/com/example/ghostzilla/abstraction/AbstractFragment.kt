package com.example.ghostzilla.abstraction

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

abstract class AbstractFragment<T : ViewDataBinding>(contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    lateinit var binding: T

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()
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