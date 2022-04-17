package com.rdp.ghostium.ui.tabs.articles

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.abstraction.listeners.ItemOnClickListener
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import com.rdp.ghostium.network.guardian.GuardianRemoteRepository
import com.rdp.ghostium.ui.tabs.articles.recycler.ArticlesAdapter
import com.rdp.ghostium.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val guardianRemoteRepository: GuardianRemoteRepository,
    application: Application
) : AbstractViewModel(application), ItemOnClickListener {

    private var callbacks: (
        data: LocalModel, title: TextView?,
        subTitle: TextView?, imageView: ImageView?
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
            imageView: ImageView?
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
