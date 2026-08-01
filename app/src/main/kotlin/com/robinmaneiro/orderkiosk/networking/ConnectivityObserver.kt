package com.robinmaneiro.orderkiosk.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ConnectivityObserver(context: Context) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    fun observe(): Flow<Status> = callbackFlow {
        if (connectivityManager == null) {
            trySend(Status.Lost)
            awaitClose()
            return@callbackFlow
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network is connected, but we wait for validation
            }

            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                val isInternetValid = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                trySend(if (isInternetValid) Status.Available else Status.Lost)
            }

            override fun onLost(network: Network) {
                trySend(Status.Lost)
            }
        }

        // Register the callback
        connectivityManager.registerDefaultNetworkCallback(callback)

        // SEND INITIAL STATE: Check if currently connected immediately
        val initialStatus = connectivityManager.activeNetwork?.let { network ->
            val caps = connectivityManager.getNetworkCapabilities(network)
            if (caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true) {
                Status.Available
            } else {
                Status.Lost
            }
        } ?: Status.Lost
        trySend(initialStatus)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged() // Prevents UI flicker from duplicate updates

    enum class Status { Available, Lost }
}
