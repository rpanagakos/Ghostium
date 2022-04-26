package com.rdp.ghostium.ui.tabs.settings.bookmark

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.models.errors.mapper.NO_ARTICLES
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.ui.tabs.articles.recycler.ArticlesAdapter
import com.rdp.ghostium.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    localRepository: LocalRepository,
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
