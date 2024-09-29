package com.solopov.common.data.network

import androidx.annotation.StringRes
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager

sealed class ApiError(
    @StringRes val defaultMessageRes: Int,
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause) {

    class FailedAuthorizationException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_invalid_token, message, cause)

    class NoInternetException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_connection, message, cause)

    class ServerException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_server, message, cause)

    class EmptyResponseException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_unknown, message, cause)

    class HostException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_server, message, cause)

    class NotFoundException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_not_found_update, message, cause)

    class UnknownException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_unknown, message, cause)

    class ConflictException(
        message: String? = null,
        cause: Throwable? = null,
    ) : ApiError(R.string.error_conflict)

    class CoroutineException : ApiError(R.string.error_unknown)

    class SecurityException : ApiError(R.string.error_unknown)

    fun getMessage(resourceManager: ResourceManager): String {
        return if (message?.isBlank() == true) "" else resourceManager.getString(defaultMessageRes)
    }
}

fun Throwable.getMessage(resourceManager: ResourceManager): String = when (this) {
    is ApiError -> getMessage(resourceManager)
    else -> resourceManager.getString(R.string.error_unknown)
}

data class ErrorDto(
    val type: String?,
    val message: String?,
)
