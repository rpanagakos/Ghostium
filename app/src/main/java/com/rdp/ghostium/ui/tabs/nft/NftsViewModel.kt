package com.rdp.ghostium.ui.tabs.nft

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.opensea.Assets
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.network.DataRepositorySource
import com.rdp.ghostium.ui.tabs.common.recycler.TabsAdapter
import com.rdp.ghostium.utils.NetworkConnectivity
import com.rdp.ghostium.utils.SingleLiveEvent
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NftsViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    val nftAdapter: TabsAdapter = TabsAdapter(this)
    val nftsLiveData = SingleLiveEvent<Assets>()
    val displayMessage = MutableLiveData<Boolean>(false)

    fun runOperation() {
        getAllNfts()
    }

    fun getAllNfts() {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getAllNfts().collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            displayMessage.value = false
                            nftsLiveData.postValue(it)
                            nftAdapter.submitList(it.assets as List<LocalModel>)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->

                        }
                    }
                }
            }
        }
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) {
        TODO("Not yet implemented")
    }

}