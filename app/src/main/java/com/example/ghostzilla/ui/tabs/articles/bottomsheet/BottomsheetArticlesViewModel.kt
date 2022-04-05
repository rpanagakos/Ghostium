package com.example.ghostzilla.ui.tabs.articles.bottomsheet

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BottomsheetArticlesViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application) {

    private var callbacks: (data: LocalModel) -> Unit = { _ -> }
    val isSaved = SingleLiveEvent<Boolean>()

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun runOperation(article: Article, listener: (data: LocalModel) -> Unit) {
        isArticleInDB(article.id)
        this.callbacks = listener
    }

    private fun isArticleInDB(articleId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localRepository.isSavedArticle(articleId)
            }.onSuccess {
                isSaved.postValue(it)
            }
        }
    }

    fun articleActionOnClick(article: Article) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                if (isSaved.value == true)
                    localRepository.deleteArticle(article)
                else
                    localRepository.insertFavouriteArticle(article)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    callbacks.invoke(article)
                }
            }.onFailure {
                callbacks.invoke(article)
            }
        }
    }


}