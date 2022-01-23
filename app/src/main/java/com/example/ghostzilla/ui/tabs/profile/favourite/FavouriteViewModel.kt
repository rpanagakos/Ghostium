package com.example.ghostzilla.ui.tabs.profile.favourite

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

    var cryptos: LiveData<MutableList<CryptoItemDB>> = localRepository.fetchFavouriteCryptos().asLiveData()
    val favouriteAdapter: FavouriteAdapter = FavouriteAdapter(this, this)
    var checkBoxIsVisible = MutableLiveData<Boolean>(false)

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

    fun test(cryptoItemDB: CryptoItemDB){
       /* cryptos.value!!.forEachIndexed { index, _ ->
            if (cryptos.value!![index].id == cryptoItemDB.id) {
                cryptos.value!![index].isSelected = !cryptos.value!![index].isSelected
            }
        }
        favouriteAdapter.submitList(cryptos.value as List<LocalModel>)*/
        cryptoItemDB.isSelected = !cryptoItemDB.isSelected
        favouriteAdapter.notifyDataSetChanged()
        checkBoxIsVisible.postValue(true)
    }

    override fun onClick(
        data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: CircleImageView
    ) {
        callbacks.invoke(data, title, subTitle, circleImageView)
    }

    override fun onLongClick() {
    }

}
