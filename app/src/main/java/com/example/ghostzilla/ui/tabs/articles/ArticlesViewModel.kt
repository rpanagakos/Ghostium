package com.example.ghostzilla.ui.tabs.articles

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.lottie.LottieAnimationView
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ArticleClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.network.guardian.GuardianRemoteRepository
import com.example.ghostzilla.ui.tabs.articles.recycler.ArticlesAdapter
import com.example.ghostzilla.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    private val guardianRemoteRepository: GuardianRemoteRepository,
    application: Application
) : AbstractViewModel(application), ArticleClickListener {

    lateinit var cryptoItem: CryptoItemDB
    private var callbacks: (data: LocalModel) -> Unit = { _ -> }

    val articlesPagingAdapter by lazy {
        ArticlesAdapter {
            callbacks.invoke(it)
        }
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun runOperation(listener: (data: LocalModel) -> Unit) {
        this.callbacks = listener
    }

    suspend fun getArticlesList(): LiveData<PagingData<LocalModel>> {
        return guardianRemoteRepository.getLatestNews().cachedIn(viewModelScope)
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
    }

}
