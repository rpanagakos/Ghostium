package com.example.ghostzilla.ui.tabs.articles.bottomsheet

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.utils.*
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
    val saveOption = SingleLiveEvent<AppOption>()
    val shareOption = SingleLiveEvent<AppOption>()

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
                if (it)
                    saveOption.postValue(
                        AppOption(
                            context.resources.getString(R.string.unsave_article),
                            R.drawable.ic_bookmark_filled,
                            AppOption.SettingType.SAVE_ARTICLE
                        )
                    )
                else
                    saveOption.postValue(
                        AppOption(
                            context.resources.getString(R.string.save_article),
                            R.drawable.ic_bookmark_light,
                            AppOption.SettingType.SAVE_ARTICLE
                        )
                    )
                shareOption.postValue(
                    AppOption(
                        context.resources.getString(R.string.share_article),
                        R.drawable.ic_share_light,
                        AppOption.SettingType.SHARE_ARTICLE
                    )
                )
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
                    Toast.makeText(
                        context,
                        if (isSaved.value == true)
                            context.getString(R.string.unsaved)
                        else
                            context.getString(R.string.saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.onFailure {
                callbacks.invoke(article)
            }
        }
    }

    fun favouriteOnClick(article: Article, lottieAnimationView: LottieAnimationView) {
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


}