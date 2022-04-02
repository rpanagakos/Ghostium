package com.example.ghostzilla.ui.tabs.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.lottie.LottieAnimationView
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ArticleClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.guardian.GuardianResponse
import com.example.ghostzilla.models.guardian.Result
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.network.guardian.GuardianRemoteRepository
import com.example.ghostzilla.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    private val guardianRemoteRepository: GuardianRemoteRepository,
    application: Application
) : AbstractViewModel(application), ArticleClickListener {

    lateinit var cryptoItem: CryptoItemDB
    var trendingTitle = SingleLiveEvent<GuardianResponse>()
    private val _cryptoList = MutableLiveData<PagingData<Result>>()

    private var callbacks: (
        data: LocalModel
    ) -> Unit = { _ -> }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity


    fun runOperation(
        listener: (
            data: LocalModel
        ) -> Unit
    ) {
        this.callbacks = listener
        getLatestNews()
    }

    suspend fun getCryptoList(): LiveData<PagingData<Result>> {
        val response = guardianRemoteRepository.getLatestNewsDummy().cachedIn(viewModelScope)
        _cryptoList.value = response.value
        return response
    }

    private fun getLatestNews() {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.getLatestNews(
                    Constants.GUARDIAN_CONTENT,
                    "newest",
                    Constants.GUARDIAN_FIELDS
                ).collect { response ->
                    when (response) {
                        is GenericResponse.Success -> response.data?.let {
                            trendingTitle.postValue(it)
                        } ?: run { showToastMessage(0) }
                        is GenericResponse.DataError -> response.errorCode?.let { error ->
                            checkErrorCode(error)
                        }
                    }
                }
            }
        }
    }

    fun favouriteOnClick(lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.disable()
        if (lottieAnimationView.progress > Constants.LOTTIE_STARTING_STATE) {
            viewModelScope.launch(Dispatchers.Default) {
                kotlin.runCatching {
                    localRepository.deleteCrypto(cryptoItem)
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        lottieAnimationView.progress = 0f
                        lottieAnimationView.enable()
                    }
                }.onFailure {
                    lottieAnimationView.enable()
                }
            }
        } else if (lottieAnimationView.progress == Constants.LOTTIE_STARTING_STATE) {
            viewModelScope.launch(Dispatchers.Default) {
                kotlin.runCatching {
                    localRepository.insertFavouriteCrypto(cryptoItem)
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        lottieAnimationView.enableAfterAnimation()
                    }
                }.onFailure {
                    lottieAnimationView.enable()
                }
            }
        }
    }

    override fun articleOnClick(data: LocalModel) {
        TODO("Not yet implemented")
    }

}
