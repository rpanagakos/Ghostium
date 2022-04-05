package com.example.ghostzilla.ui.tabs.articles

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.ArticleClickListener
import com.example.ghostzilla.abstraction.listeners.ItemOnClickListener
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
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel, title: TextView?,
        subTitle: TextView?, circleImageView: ImageView?
    ) -> Unit = { _, _, _, _ -> }

    val articlesPagingAdapter by lazy {
        ArticlesAdapter(this) { callbacks.invoke(it, null, null, null) }
    }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView?,
            subTitle: TextView?,
            circleImageView: ImageView?
        ) -> Unit
    ) {
        this.callbacks = listener
    }

    suspend fun getArticlesList(title: TitleRecyclerItem): LiveData<PagingData<LocalModel>> {
        return guardianRemoteRepository.getLatestNews(title = title).cachedIn(viewModelScope)
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        imageView: ImageView
    ) {
        callbacks.invoke(data, title, subTitle, imageView)
    }


}
