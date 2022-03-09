package com.example.ghostzilla.ui.tabs.cryptos

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.ui.tabs.common.TabsAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) -> Unit = { _, _, _, _ -> }
    val trendsAdapter: TabsAdapter by lazy {
        TabsAdapter(this, dataRepository.currencyImpl)
    }

    var trendingTitle = MutableLiveData<TitleRecyclerItem>()
    var topTitle = MutableLiveData<TitleRecyclerItem>()
    var trendingCryptos: LiveData<MutableList<TredingCoins>> =
        localRepository.fetchTrendingCryptos().asLiveData()

    var cryptosJob: Job? = null

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView,
            subTitle: TextView?,
            circleImageView: ImageView
        ) -> Unit
    ) {
        this.callbacks = listener
        //if (networkConnectivity.isConnected())
            getAllCryptos()
        /*else {
            checkErrorCode(NO_INTERNET_CONNECTION)
            displayMessage.value = true
        }*/
    }

    fun getAllCryptos() {
        if ((cryptosJob?.isActive == false || cryptosJob == null)) {
            cryptosJob = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(40)) {
                wrapEspressoIdlingResource {
                    dataRepository.requestData().collect { response ->
                        when (response) {
                            is GenericResponse.Success -> response.data?.let {
                                trendsAdapter.submitList(getCryptoList(it))
                            } ?: run { showToastMessage(0) }
                            is GenericResponse.DataError -> response.errorCode?.let { error ->
                                checkErrorCode(error)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cryptosJob?.cancel()
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) {
        callbacks.invoke(data, title, subTitle, circleImageView)
    }

    private fun getCryptoList(listCryptos: Cryptos): List<LocalModel> {
        val list = listCryptos.CryptosList
        return trendingCryptos.value?.let {
            mutableListOf(trendingTitle.value!!).plus(it[0]).plus(topTitle.value!!).plus(list)
        } ?: mutableListOf(topTitle.value!!).plus(list)
    }

}