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
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.di.common.CurrencySource
import com.rdp.ghostium.models.coingecko.search.CoinResult
import com.rdp.ghostium.models.coingecko.search.CoinsSearched
import com.rdp.ghostium.models.coingecko.shimmer.CoinsSearchShimmer
import com.rdp.ghostium.models.errors.mapper.NOT_FOUND
import com.rdp.ghostium.models.errors.mapper.NO_SEARCHES
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.settings.RecentlyItem
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.network.DataRepositorySource
import com.rdp.ghostium.ui.tabs.common.recycler.TabsAdapter
import com.rdp.ghostium.utils.SingleLiveEvent
import com.rdp.ghostium.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource,
    private val localRepository: LocalRepository,
    currencyImpl: CurrencySource,
    application: Application
) : AbstractViewModel(application), GeneralClickListener, ItemOnClickListener {

    val displayMessage = MutableLiveData<Boolean>(false)
    val coinsAnswer = MutableLiveData<CoinsSearched>()

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
            currencyImpl = currencyImpl,
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
        if (searchAdapter.itemCount == 0 || searchAdapter.currentList[0] is TitleRecyclerItem)
            getSearches()
    }

    fun searchCoins(coinID: String) {
        displayMessage.postValue(false)
        searchAdapter.submitList(listOf(CoinsSearchShimmer(), CoinsSearchShimmer(), CoinsSearchShimmer(), CoinsSearchShimmer(), CoinsSearchShimmer()))
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.searchCoins(coinID).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            if (it.coinResults.isEmpty()) {
                                resultNotFound.postValue(NOT_FOUND)
                                displayMessage.postValue(true)
                            } else {
                                coinsAnswer.postValue(it)
                                displayMessage.postValue(false)
                                searchAdapter.submitList(it.coinResults)
                            }
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                            if (displayMessage.value == false)
                                displayMessage.postValue(true)
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
                        mutableListOf<LocalModel>(
                            searchTitle.value
                                ?: TitleRecyclerItem(context.getString(R.string.recently_searches))
                        )
                    searchAdapter.submitList(list.plus(it))
                    displayMessage.value = false
                }
            }
        }
    }

    fun clearSearch() {
        searchAdapter.submitList(null)
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
        if (data is CoinResult) {
            viewModelScope.launch {
                localRepository.insertRecentItem(RecentlyItem(data.name))
            }
        }
        callbacks.invoke(data, title, subTitle, circleImageView)
    }
}