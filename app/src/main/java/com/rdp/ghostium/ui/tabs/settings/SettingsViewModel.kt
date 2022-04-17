package com.rdp.ghostium.ui.tabs.settings

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.ui.tabs.common.TabsAdapter
import com.rdp.ghostium.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel,
        contractName: TextView,
    ) -> Unit = { _, _ -> }

    val optionsAdapter: TabsAdapter = TabsAdapter(this)

    fun runOperation(
        list: List<LocalModel>,
        listener: (
            data: LocalModel,
            contractName: TextView,
        ) -> Unit
    ) {
        this.callbacks = listener
        optionsAdapter.submitList(list)
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTtitle: TextView?,
        circleImageView: ImageView
    ) {
        callbacks.invoke(data, title)

    }

}
