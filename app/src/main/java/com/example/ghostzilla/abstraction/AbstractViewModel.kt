package com.example.ghostzilla.abstraction

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import com.example.ghostzilla.models.errors.ErrorManager
import com.example.ghostzilla.models.errors.mapper.NETWORK_ERROR
import com.example.ghostzilla.models.errors.mapper.NOT_FOUND
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import com.example.ghostzilla.utils.SingleLiveEvent
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