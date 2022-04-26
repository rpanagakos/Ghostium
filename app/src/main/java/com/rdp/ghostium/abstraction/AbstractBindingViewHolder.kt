package com.rdp.ghostium.abstraction

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractBindingViewHolder(itemView: ViewDataBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    abstract fun present(data: LocalModel, isLastElement: Boolean = false)
}