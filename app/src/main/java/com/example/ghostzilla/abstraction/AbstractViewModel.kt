package com.example.ghostzilla.abstraction

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghostzilla.models.errors.ErrorManager
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class AbstractViewModel : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = SingleLiveEvent<Any>()
    val showToast: SingleLiveEvent<Any> get() = showToastPrivate

    @Inject
    lateinit var errorManager: ErrorManager

    protected fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = error.description
    }

    protected fun CoroutineScope.launchPeriodicAsync(repeatMillis: Long, action: suspend () -> Unit) = this.async {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    }
}