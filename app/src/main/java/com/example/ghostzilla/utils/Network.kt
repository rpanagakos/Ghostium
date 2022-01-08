package com.example.ghostzilla.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.example.ghostzilla.models.errors.mapper.NETWORK_ERROR
import com.example.ghostzilla.models.errors.mapper.NO_INTERNET_CONNECTION
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class Network @Inject constructor(val context: Context) : NetworkConnectivity {

    override suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    override fun registerNetworkCallback(isOnline: () -> Unit, isOffline: () -> Unit) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isOnline.invoke()
                }

                override fun onLost(network: Network) {
                    isOffline.invoke()
                }
            }
            )
        } catch (e: Exception) {
            isOffline.invoke()
        }
    }

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}

interface NetworkConnectivity {
    suspend fun processCall(responseCall: suspend () -> Response<*>): Any?
    fun registerNetworkCallback(
        isOnline: () -> Unit,
        isOffline: () -> Unit
    )
    fun isConnected(): Boolean
}