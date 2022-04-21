package com.rdp.ghostium.ui.tabs.common.recycler

import androidx.databinding.ViewDataBinding
import com.rdp.ghostium.abstraction.AbstractBindingViewHolder
import com.rdp.ghostium.abstraction.LocalModel

class ShimmerViewHolder (val binding: ViewDataBinding) : AbstractBindingViewHolder(binding) {

    override fun present(data: LocalModel, isLastElement: Boolean) {}
}