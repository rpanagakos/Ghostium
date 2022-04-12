package com.example.ghostzilla.ui.tabs.settings.bookmark

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.models.errors.mapper.NO_ARTICLES
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.ui.tabs.articles.recycler.ArticlesAdapter
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    var articles: LiveData<MutableList<Article>> =
        localRepository.fetchAllArticles().asLiveData()

    val ids = mutableListOf<String>()
    val isEmpty = MutableLiveData(false)

    private var callbacks: (
        data: LocalModel, title: TextView?,
        subTitle: TextView?, imageView: ImageView?
    ) -> Unit = { _, _, _, _ -> }

    val articlesPagingAdapter by lazy {
        ArticlesAdapter(this) { callbacks.invoke(it, null, null, null) }
    }

    fun runOperation(
        articleList: PagingData<LocalModel>,
        listener: (
            data: LocalModel, title: TextView?,
            subTitle: TextView?, imageView: ImageView?
        ) -> Unit
    ) {
        this.callbacks = listener
        if (articles.value?.isNotEmpty() == true) {
            viewModelScope.launch {
                articlesPagingAdapter.submitData(articleList)
            }
        } else {
            resultNotFound.postValue(NO_ARTICLES)
            isEmpty.postValue(true)
        }
    }

    override fun onClick(
        data: LocalModel, title: TextView,
        subTitle: TextView?, imageView: ImageView
    ) {
        callbacks.invoke(data, title, subTitle, imageView)
    }

}
