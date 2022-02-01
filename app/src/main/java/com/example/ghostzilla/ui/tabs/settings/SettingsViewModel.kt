package com.example.ghostzilla.ui.tabs.settings

import android.app.Application
import android.widget.TextView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.common.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel,
        contractName: TextView,
    ) -> Unit = { _, _ -> }

    val optionsAdapter: TabsAdapter = TabsAdapter(this)

    fun runOperation(
        list : List<LocalModel>,
        listener: (
            data: LocalModel,
            contractName: TextView,
        ) -> Unit
    ){
        this.callbacks = listener
        optionsAdapter.submitList(list)
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTtitle: TextView?,
        circleImageView: CircleImageView
    ) {
        callbacks.invoke(data, title)

    }

}
