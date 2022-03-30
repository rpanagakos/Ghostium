package com.example.ghostzilla.ui.tabs.common

import androidx.databinding.ViewDataBinding
import com.example.ghostzilla.abstraction.AbstractBindingViewHolder
import com.example.ghostzilla.abstraction.LocalModel

class ShimmerViewHolder (val binding: ViewDataBinding) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel, isLastElement: Boolean) {}
}