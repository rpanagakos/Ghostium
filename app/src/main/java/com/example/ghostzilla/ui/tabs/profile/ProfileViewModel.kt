package com.example.ghostzilla.ui.tabs.profile

import android.app.Application
import android.widget.TextView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.account.OptionItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel,
        contractName: TextView,
    ) -> Unit = { _, _ -> }

    private val optionsList = listOf(
        OptionItem(R.drawable.ic_photo_album, context.getString(R.string.option_nft)),
        OptionItem(R.drawable.ic_bitcoin, context.getString(R.string.option_cryptos)),
        OptionItem(R.drawable.ic_wallet_filled, context.getString(R.string.options_wallet)),
        OptionItem(R.drawable.ic_baseline_info_24, context.getString(R.string.option_about))
    )

    val optionsAdapter: TabsAdapter = TabsAdapter(this)

    fun runOperation(
        listener: (
            data: LocalModel,
            contractName: TextView,
        ) -> Unit
    ){
        this.callbacks = listener
        optionsAdapter.submitList(optionsList)
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
