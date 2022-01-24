package com.example.ghostzilla.ui.tabs.settings.favourite

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    application: Application
) : AbstractViewModel(application), FavouriteClickListener {

    private var callbacks: (
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: CircleImageView
    ) -> Unit = { _, _, _, _ -> }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    var cryptos: LiveData<MutableList<CryptoItemDB>> =
        localRepository.fetchFavouriteCryptos().asLiveData()
    val favouriteAdapter: FavouriteAdapter = FavouriteAdapter(this, this)
    var cryptosChosen = mutableListOf<CryptoItemDB>()
    val isProcessing = MutableLiveData<Boolean>(false)

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView,
            subTitle: TextView?,
            circleImageView: CircleImageView
        ) -> Unit
    ) {
        this.callbacks = listener
        if (cryptos.value?.isNotEmpty() == true) {
            var cryptosIds = cryptos.value!![0].id
            cryptos.value!!.drop(1).forEach { cryptoItem ->
                cryptosIds = "$cryptosIds,${cryptoItem.id}"
            }
            getFavouriteCryptosPrices(cryptosIds)
        } else
            favouriteAdapter.submitList(emptyList())
    }

    private fun getFavouriteCryptosPrices(cryptosIds: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getFavouritesPrices(cryptosIds).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let { responseJson ->
                            cryptos.value?.forEach { cryptoItem ->
                                cryptoItem.currentPrice =
                                    responseJson.get(cryptoItem.id).asJsonObject.get("eur").asDouble
                            }
                            favouriteAdapter.submitList(cryptos.value as List<LocalModel>)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            println(error)
                        }
                    }
                }
            }
        }
    }

    fun processFavCrypto(cryptoItemDB: CryptoItemDB, dataLocation: Int) {
        cryptoItemDB.isSelected = !cryptoItemDB.isSelected
        if (cryptoItemDB.isSelected) {
            if (isProcessing.value == false) isProcessing.postValue(true)
            if (!cryptosChosen.isNullOrEmpty() && !cryptosChosen.contains(cryptoItemDB))
                cryptosChosen.add(cryptoItemDB)
        } else if (!cryptosChosen.isNullOrEmpty() && cryptosChosen.contains(
                cryptoItemDB
            )
        )
            cryptosChosen.remove(cryptoItemDB)

        favouriteAdapter.notifyItemChanged(dataLocation)
    }

    fun checkState(checkState: Boolean) {
        //this one needs refactor its not  good practice
        if (checkState) {
            cryptos.value?.forEach {
                if (!it.isSelected) {
                    cryptosChosen.add(it)
                    it.isSelected = true
                }
            }
            favouriteAdapter.notifyDataSetChanged()
        } else {
            cryptosChosen.clear()
            cryptos.value?.forEach {
                if (it.isSelected) {
                    it.isSelected = false
                }
            }
            favouriteAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: CircleImageView,
        position: Int
    ) {
        if (isProcessing.value == true)
            processFavCrypto(data as CryptoItemDB, position)
        else
            callbacks.invoke(data, title, subTitle, circleImageView)
    }


}