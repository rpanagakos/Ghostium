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

    val optionsList = listOf(
        OptionItem(R.drawable.ic_twitter, "Beloved Nfts"),
        OptionItem(R.drawable.ic_twitter, "Favourite Cryptos"),
        OptionItem(R.drawable.ic_twitter, "Connect you wallet"),
        OptionItem(R.drawable.ic_twitter, "About")
    )

    val optionsAdapter: TabsAdapter = TabsAdapter(this)

    fun runOperation(){
        optionsAdapter.submitList(optionsList)
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity
    override fun onClick(
        data: LocalModel,
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) {}

}
