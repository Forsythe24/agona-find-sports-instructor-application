package com.solopov.common.data.network.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
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
    connectivityManager: ConnectivityManager,
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
            initialValue = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true,
        )

    override val isNetworkAvailable = connectivityManager.isDefaultNetworkActive

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private fun ConnectivityManager.networkChangesFlow() = callbackFlow {
        val listener = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }

        registerNetworkCallback(networkRequest, listener)
    }
}
