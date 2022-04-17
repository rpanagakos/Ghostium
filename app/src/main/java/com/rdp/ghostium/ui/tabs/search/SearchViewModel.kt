package com.rdp.ghostium.ui.tabs.search

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.GeneralClickListener
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.models.errors.mapper.NO_SEARCHES
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.settings.RecentlyItem
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.ui.tabs.common.TabsAdapter
import com.rdp.ghostium.utils.SingleLiveEvent
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), GeneralClickListener, ItemOnClickListener {

    val displayMessage = MutableLiveData<Boolean>(false)

    private var callbacks: (
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) -> Unit = { _, _, _, _ -> }

    val searches = SingleLiveEvent<MutableList<RecentlyItem>>()
    val searchText = SingleLiveEvent<String?>()
    val searchTitle = SingleLiveEvent<TitleRecyclerItem>()

    val searchAdapter: TabsAdapter =
        TabsAdapter(
            listener = this,
            currencyImpl = dataRepository.currencyImpl,
            generalClickListener = this
        )

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView,
            subTitle: TextView?,
            circleImageView: ImageView
        ) -> Unit
    ) {
        this.callbacks = listener
        getSearches()
    }

    fun searchCoin(coinID: String) {
        displayMessage.postValue(false)
        searchAdapter.submitList(listOf(CryptoShimmer(),CryptoShimmer(),CryptoShimmer()))
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoin(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            displayMessage.postValue(false)
                            val cryptoDetails = CryptoItem(
                                currentPrice = it.marketData.currentPrice.getPrice(dataRepository.currencyImpl.getCurrency()),
                                id = it.id,
                                image = it.image.thumb,
                                name = it.name,
                                priceChangePercentage24h = it.marketData.priceChangePercentage24h,
                                symbol = it.symbol
                            )
                            searchAdapter.submitList(listOf(cryptoDetails) as List<LocalModel>)
                            localRepository.insertRecentItem(RecentlyItem(it.id))
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                            if (displayMessage.value == false)
                                displayMessage.postValue( true)
                        }
                    }
                }
            }

        }
    }

    private fun getSearches() {
        viewModelScope.launch {
            kotlin.runCatching {
                localRepository.fetchRecentlySearches()
            }.onSuccess {
                if (it.isEmpty()) {
                    resultNotFound.postValue(NO_SEARCHES)
                    displayMessage.postValue(true)
                } else {
                    val list =
                        mutableListOf<LocalModel>(searchTitle.value ?: TitleRecyclerItem(context.getString(R.string.recently_searches)))
                    searchAdapter.submitList(list.plus(it))
                    displayMessage.value = false
                }
            }
        }
    }

    fun clearSearch() {
        displayMessage.value = false
        getSearches()
    }

    override fun onClick(data: LocalModel, position: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                if (position == 0)
                    localRepository.deleteRecentItem(data as RecentlyItem)
                else
                    searchText.postValue((data as RecentlyItem).searchedText)
            }.onSuccess {
                if (position == 0) getSearches()
            }
        }
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: ImageView
    ) {
        callbacks.invoke(data, title, subTitle, circleImageView)
    }
}