package com.example.ghostzilla.abstraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghostzilla.models.errors.ErrorManager
import com.example.ghostzilla.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class AbstractViewModel : ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}