package com.example.ghostzilla.abstraction

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghostzilla.models.errors.ErrorManager
import com.example.ghostzilla.models.errors.mapper.NETWORK_ERROR
import com.example.ghostzilla.models.errors.mapper.NOT_FOUND
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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

    protected fun checkErrorCode(errorCode: Int){
        when(errorCode){
            NO_INTERNET_CONNECTION, NETWORK_ERROR ,NOT_FOUND -> {
                resultNotFoundPrivate.value = errorCode
            }
            else -> showToastMessage(errorCode)
        }
    }

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