package com.example.ghostzilla.ui.tabs.nft

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.ItemOnClickListener
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.opensea.Assets
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NftsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
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
        contractName: TextView,
        contractTickerSumbol: TextView,
        circleImageView: CircleImageView
    ) {
        TODO("Not yet implemented")
    }

}