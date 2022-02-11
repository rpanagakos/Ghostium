package com.example.ghostzilla.ui.tabs.search

import android.app.Application
import android.widget.TextView
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.settings.RecentlyItem
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.common.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    private val list = listOf(
        TitleRecyclerItem("RecentlyUsed"),
        RecentlyItem("Gala"),
        RecentlyItem("Gala"),
        RecentlyItem("Gala"),
        RecentlyItem("Gala")
    )

    val searchAdapter: TabsAdapter = TabsAdapter(this, dataRepository.currencyImpl)

    fun runOperation() {
        searchAdapter.submitList(list)
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: CircleImageView
    ) {
    }
}