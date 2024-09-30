package com.solopov.common.data.network.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

interface NetworkStateProvider {
    val state: StateFlow<Boolean>
    val isNetworkAvailable: Boolean
}

@SuppressLint("MissingPermission")
class NetworkStateProviderImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val scope: CoroutineScope,
) : NetworkStateProvider {

    override val state = connectivityManager
        .networkChangesFlow()
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 1_000,
                replayExpirationMillis = 1_000
            ),
            initialValue = isNetworkAvailable,  // This now checks for older versions as well
        )

    override val isNetworkAvailable: Boolean
        get() {
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            }
        }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private fun ConnectivityManager.networkChangesFlow() = callbackFlow {
        val listener = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }

            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }
        }

        registerNetworkCallback(networkRequest, listener)

        awaitClose {
            unregisterNetworkCallback(listener)
        }
    }
}
