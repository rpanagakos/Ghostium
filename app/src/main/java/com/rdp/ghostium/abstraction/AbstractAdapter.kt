package com.rdp.ghostium.abstraction

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractAdapter : ListAdapter<LocalModel, RecyclerView.ViewHolder>(DiffUtilClass<LocalModel>())