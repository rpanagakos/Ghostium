package com.rdp.ghostium.ui.tabs.articles

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application){

    private var shareCallback: (data: Article) -> Unit = { _ -> }
    val isBookmarked = SingleLiveEvent<Boolean>()

    fun runOperation(article: Article, listener: (data: Article) -> Unit) {
        isArticleInDB(article.id)
        this.shareCallback = listener
    }

    private fun isArticleInDB(articleId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localRepository.isSavedArticle(articleId)
            }.onSuccess {
                isBookmarked.postValue(it)
            }
        }
    }

    fun bookmarkOnClick(article: Article, lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.disable()
        if (lottieAnimationView.progress > Constants.LOTTIE_STARTING_STATE) {
            viewModelScope.launch(Dispatchers.Default) {
                kotlin.runCatching {
                    localRepository.deleteArticle(article)
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
                    localRepository.insertFavouriteArticle(article = article)
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

    fun shareClickEvent(article: Article){
        shareCallback.invoke(article)
    }
}