package com.rdp.ghostium.abstraction

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractPagingAdapter : PagingDataAdapter<LocalModel, RecyclerView.ViewHolder>(DiffUtilClass<LocalModel>())