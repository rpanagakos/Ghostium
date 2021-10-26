package com.example.ghostzilla.abstraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghostzilla.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class AbstractViewModel : ViewModel() {

    val errorMessage = SingleLiveEvent<String>()
    val internetConnection = SingleLiveEvent<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    protected fun handleFailures(throwable: Throwable?) {
        when (throwable) {
            is SocketTimeoutException -> {
                internetConnection.postValue(true)
            }
            is UnknownHostException -> {
                internetConnection.postValue(true)
            }
            else -> {
                internetConnection.postValue(false)
            }
        }
    }

    protected fun onError(message: String) {
        errorMessage.value = message
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}