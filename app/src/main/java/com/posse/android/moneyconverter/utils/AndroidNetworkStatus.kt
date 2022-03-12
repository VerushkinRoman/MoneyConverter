package com.posse.android.moneyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AndroidNetworkStatus @Inject constructor(context: Context) : NetworkStatus {

    private val statusSubject: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    init {

        val connectivityManager = context.getSystemService<ConnectivityManager>()
        val request = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    statusSubject.value = true
                }

                override fun onUnavailable() {
                    statusSubject.value = false
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    statusSubject.value = false
                }

                override fun onLost(network: Network) {
                    statusSubject.value = false
                }
            })
    }

    override fun isOnline(): StateFlow<Boolean?> = statusSubject
}