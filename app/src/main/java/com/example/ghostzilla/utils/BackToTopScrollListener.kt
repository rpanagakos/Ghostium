package com.example.ghostzilla.utils

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.databinding.FragmentTrendsBinding

abstract class BackToTopScrollListener(val binding: ViewDataBinding) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when(recyclerView.layoutManager){
            is LinearLayoutManager -> {
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1
                when(binding){
                    is FragmentTrendsBinding -> binding.setVariable(BR.backToTop, position >= 12)
                }
            }
        }
    }
}