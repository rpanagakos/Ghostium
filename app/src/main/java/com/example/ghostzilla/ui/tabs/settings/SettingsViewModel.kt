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

    private val settingsList = listOf(
        AppOption(context.getString(R.string.option_language),R.drawable.ic_baseline_info_24, AppOption.SettingType.LANGUAGE ),
        AppOption(context.getString(R.string.option_currency),R.drawable.ic_baseline_info_24, AppOption.SettingType.CURRENCY ),
        AppOption(context.getString(R.string.option_cryptos),R.drawable.ic_baseline_info_24, AppOption.SettingType.CRYPTO_FAV ),
        AppOption(context.getString(R.string.option_articles),R.drawable.ic_baseline_info_24, AppOption.SettingType.NEWS_FAV ),
        AppOption(context.getString(R.string.option_share),R.drawable.ic_baseline_info_24, AppOption.SettingType.SHARE_APP ),
        AppOption(context.getString(R.string.option_rate),R.drawable.ic_baseline_info_24, AppOption.SettingType.RATE_APP ),
        AppOption(context.getString(R.string.option_contact),R.drawable.ic_baseline_info_24, AppOption.SettingType.CONTACT_US ),
    )

    val optionsAdapter: TabsAdapter = TabsAdapter(this)

    fun runOperation(
        listener: (
            data: LocalModel,
            contractName: TextView,
        ) -> Unit
    ){
        this.callbacks = listener
        optionsAdapter.submitList(settingsList)
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
