package com.example.ghostzilla.abstraction

import androidx.lifecycle.ViewModel
import com.example.ghostzilla.utils.SingleLiveEvent
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class AbstractViewModel : ViewModel() {

    val internetConnection = SingleLiveEvent<Boolean>()

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
}