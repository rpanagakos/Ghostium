package com.rdp.ghostium.abstraction

import android.app.Application
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.rdp.ghostium.R
import com.rdp.ghostium.connectivity.ConnectionState
import com.rdp.ghostium.connectivity.ConnectivityObserver
import com.rdp.ghostium.models.errors.ErrorManager
import com.rdp.ghostium.models.errors.mapper.NETWORK_ERROR
import com.rdp.ghostium.models.errors.mapper.NOT_FOUND
import com.rdp.ghostium.models.errors.mapper.NO_INTERNET_CONNECTION
import com.rdp.ghostium.ui.tabs.common.recycler.TabsAdapter
import com.rdp.ghostium.utils.SingleLiveEvent
import com.rdp.ghostium.utils.showKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import javax.inject.Inject

abstract class AbstractViewModel(
    application: Application,
    private val connectivityObserver: ConnectivityObserver? = null
) : AndroidViewModel(application) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = SingleLiveEvent<Any>()
    val showToast: SingleLiveEvent<Any> get() = showToastPrivate
    val resultNotFound: SingleLiveEvent<Int> get() = resultNotFoundPrivate
    val isConnected = SingleLiveEvent<Boolean>()
    protected val context
        get() = getApplication<Application>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val resultNotFoundPrivate = SingleLiveEvent<Int>()


    @Inject
    lateinit var errorManager: ErrorManager

    protected fun checkErrorCode(errorCode: Int) {
        when (errorCode) {
            NO_INTERNET_CONNECTION, NETWORK_ERROR, NOT_FOUND -> {
                resultNotFoundPrivate.value = errorCode
            }
            else -> showToastMessage(errorCode)
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.postValue(error.description)
    }

    fun searchButton(searchButton: ImageView, searchEditText: TextInputEditText) {
        when (searchEditText.text?.isEmpty()) {
            true -> {
                searchEditText.apply {
                    requestFocus()
                    showKeyboard()
                }
            }
            else -> {
                searchEditText.setText("")
                searchButton.setImageResource(R.drawable.ic_search)
            }
        }
    }

    fun scrollToTopRecycler(recyclerView: RecyclerView) {
        when ((recyclerView.adapter as? TabsAdapter)?.currentPosition?.get()) {
            in 0..18 -> Unit
            else -> recyclerView.scrollToPosition(18)
        }
        recyclerView.smoothScrollToPosition(0)
    }

    protected fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        action: suspend () -> Unit
    ) = this.async {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    }

    protected fun observeConnectivity() {
        connectivityObserver?.let {
            it.connectionState
                .distinctUntilChanged()
                .map { it == ConnectionState.Available }
                .onEach { isConnected.postValue(it) }
                .launchIn(viewModelScope)
        }
    }
}