package com.solopov.common.utils

import com.google.firebase.FirebaseNetworkException
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.exceptions.FailedToConnectException
import com.solopov.common.data.remote.exceptions.FirebaseException
import com.solopov.common.data.remote.exceptions.SocketConnectionTimeoutException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor(
    private val resManager: ResourceManager
) {
    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is FirebaseNetworkException -> FirebaseException.FirebaseConnectionFailedException(
                resManager.getString(
                    R.string.firebase_connection_failed_exception
                )
            )


            is SocketTimeoutException -> SocketConnectionTimeoutException(
                resManager.getString(
                    R.string.socket_connection_timeout_exception
                )
            )

            is ConnectException -> FailedToConnectException(
                resManager.getString(
                    R.string.failed_to_connect_to_server_exception
                )
            )

            else -> ex
        }

    }
}
