package com.rdp.ghostium.ui.tabs.settings.favourite

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.FavouriteClickListener
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.di.IoDispatcher
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.errors.mapper.NO_CRYPTOS
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.network.DataRepositorySource
import com.rdp.ghostium.utils.NetworkConnectivity
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource,
    private val localRepository: LocalRepository,
    private val currencyImpl: CurrencySource,
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
    val favouriteAdapter: FavouriteAdapter = FavouriteAdapter(this, this, currencyImpl)
    var cryptosChosen = mutableListOf<CryptoItemDB>()
    val ids = mutableListOf<String>()
    val isProcessing = MutableLiveData<Boolean>(false)
    val isEmpty = MutableLiveData(false)

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
        } else {
            resultNotFound.postValue(NO_CRYPTOS)
            isEmpty.postValue(true)
        }
    }

    private fun getFavouriteCryptosPrices(cryptosIds: String) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getFavouritesPrices(cryptosIds).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let { responseJson ->
                            cryptos.value?.forEach { cryptoItem ->
                                cryptoItem.currentPrice =
                                    responseJson.get(cryptoItem.id).asJsonObject.get(currencyImpl.getCurrency()).asDouble
                            }
                            favouriteAdapter.submitList(cryptos.value as List<LocalModel>)
                            isEmpty.postValue(false)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            println(error)
                        }
                    }
                }
            }
        }
    }

    fun processFavCrypto(cryptoItemDB: CryptoItemDB, dataLocation: Int): Boolean {
        cryptoItemDB.isSelected = !cryptoItemDB.isSelected
        if (cryptoItemDB.isSelected) {
            if (isProcessing.value == false) isProcessing.postValue(true)
            if (!cryptosChosen.contains(cryptoItemDB)) {
                cryptosChosen.add(cryptoItemDB)
                ids.add(cryptoItemDB.id)
            }
        } else if (!cryptosChosen.isNullOrEmpty() && cryptosChosen.contains(cryptoItemDB)) {
            cryptosChosen.remove(cryptoItemDB)
            ids.remove(cryptoItemDB.id)
        }

        favouriteAdapter.notifyItemChanged(dataLocation)
        return true
    }

    fun checkState(checkState: Boolean) {
        //this one needs refactor its not  good practice
        if (checkState) {
            cryptos.value?.forEach {
                if (!it.isSelected) {
                    ids.add(it.id)
                    cryptosChosen.add(it)
                    it.isSelected = true
                }
            }
            favouriteAdapter.notifyDataSetChanged()
        } else {
            cryptosChosen.clear()
            cryptos.value?.forEach {
                if (it.isSelected) {
                    ids.remove(it.id)
                    it.isSelected = false
                }
            }
            favouriteAdapter.notifyDataSetChanged()
        }
    }

    fun dismissEveryting() {
        checkState(false)
        isProcessing.postValue(false)
    }

    fun deleteFavCryptos() {
        viewModelScope.launch(ioDispatcher) {
            kotlin.runCatching {
                if (cryptosChosen.size.equals(cryptos.value?.size))
                    localRepository.deleteAllFavourites()
                else {
                    localRepository.deleteSpecificCryptos(ids)
                }
            }.onSuccess {
                isProcessing.postValue(false)
            }
        }

    }

    fun getIntentText(): String {
        var intentText = "My Favourite Cryptos \n\n"
        cryptosChosen.forEachIndexed { index, cryptoItemDB ->
            intentText += "${index + 1}. ${cryptoItemDB.name} \n"
        }
        intentText += "\n\nThis message has been sent via Ghostium"
        return intentText
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
