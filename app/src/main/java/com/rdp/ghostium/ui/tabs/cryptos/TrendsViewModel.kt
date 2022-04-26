package com.rdp.ghostium.ui.tabs.cryptos

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.coingecko.Cryptos
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.ui.tabs.common.recycler.TabsAdapter
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    localRepository: LocalRepository,
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
    var cryptos = MutableLiveData<Cryptos>()
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
        if (trendsAdapter.currentList.size == 0)
            if (trendingCryptos.value.isNullOrEmpty())
                trendsAdapter.submitList(
                    listOf(
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer()
                    )
                )
            else
                trendsAdapter.submitList(
                    listOf(
                        trendingTitle.value,
                        trendingCryptos.value?.get(0),
                        topTitle.value,
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer(),
                        CryptoShimmer()
                    )
                )
        getAllCryptos()
        
    }

    fun getAllCryptos() {
        if ((cryptosJob?.isActive == false || cryptosJob == null)) {
            cryptosJob = viewModelScope.launchPeriodicAsync(TimeUnit.SECONDS.toMillis(40)) {
                wrapEspressoIdlingResource {
                    dataRepository.requestData().collect { response ->
                        when (response) {
                            is GenericResponse.Success -> response.data?.let {
                                cryptos.postValue(it)
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
