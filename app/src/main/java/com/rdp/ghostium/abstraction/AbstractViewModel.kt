package com.rdp.ghostium.abstraction

import android.app.Application
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R
import com.rdp.ghostium.models.errors.ErrorManager
import com.rdp.ghostium.models.errors.mapper.NETWORK_ERROR
import com.rdp.ghostium.models.errors.mapper.NOT_FOUND
import com.rdp.ghostium.models.errors.mapper.NO_INTERNET_CONNECTION
import com.rdp.ghostium.ui.tabs.common.TabsAdapter
import com.rdp.ghostium.utils.SingleLiveEvent
import com.rdp.ghostium.utils.showKeyboard
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import javax.inject.Inject

abstract class AbstractViewModel(application: Application) : AndroidViewModel(application) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = SingleLiveEvent<Any>()
    val showToast: SingleLiveEvent<Any> get() = showToastPrivate
    val resultNotFound: SingleLiveEvent<Int> get() = resultNotFoundPrivate
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
}