package com.example.ghostzilla.ui.tabs.articles

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ArticleClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.network.guardian.GuardianRemoteRepository
import com.example.ghostzilla.ui.tabs.articles.recycler.ArticlesAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val guardianRemoteRepository: GuardianRemoteRepository,
    application: Application
) : AbstractViewModel(application), ArticleClickListener {

    private var callbacks: (data: LocalModel) -> Unit = { _ -> }

    val articlesPagingAdapter by lazy {
        ArticlesAdapter { callbacks.invoke(it) }
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun runOperation( article: Article?, listener: (data: LocalModel) -> Unit) {
        this.callbacks = listener
    }

    suspend fun getArticlesList(title : TitleRecyclerItem): LiveData<PagingData<LocalModel>> {
        return guardianRemoteRepository.getLatestNews(title = title).cachedIn(viewModelScope)
    }

    override fun articleOnClick(data: LocalModel) {}

}
