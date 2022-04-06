package com.example.ghostzilla.ui.tabs.settings.bookmark

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.abstraction.listeners.FavouriteClickListener
import com.example.ghostzilla.database.room.LocalRepository
import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.di.common.CurrencyImpl
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository,
    private val currencyImpl: CurrencyImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    application: Application
) : AbstractViewModel(application), FavouriteClickListener {

    private var callbacks: (
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: CircleImageView
    ) -> Unit = { _, _, _, _ -> }

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    val ids = mutableListOf<String>()
    val isEmpty = MutableLiveData(false)

    fun runOperation(
        listener: (
            data: LocalModel,
            title: TextView,
            subTitle: TextView?,
            circleImageView: CircleImageView
        ) -> Unit
    ) {
        this.callbacks = listener
    }

    override fun onClick(
        data: LocalModel,
        title: TextView,
        subTitle: TextView?,
        circleImageView: CircleImageView,
        position: Int
    ) {
    }


}
