package com.solopov.common.handler

import com.google.firebase.FirebaseNetworkException
import com.solopov.common.data.network.exceptions.FailedToConnectException
import com.solopov.common.data.network.exceptions.FirebaseException
import com.solopov.common.data.network.exceptions.SocketConnectionTimeoutException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor() {
    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is FirebaseNetworkException -> FirebaseException.FirebaseConnectionFailedException("Remote service has failed due to network message. Inspect the device's network connectivity state or retry later to resolve")

            is SocketTimeoutException -> SocketConnectionTimeoutException("Failed to connect to server after 10 seconds. Try again later")

            is ConnectException -> FailedToConnectException("Failed to connect to the server.Check the device's connection")

            else -> ex
        }

    }
}
