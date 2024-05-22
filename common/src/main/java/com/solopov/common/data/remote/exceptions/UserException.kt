package com.solopov.common.data.remote.exceptions

sealed class UserException {
    class UserNotFound(message: String): Throwable(message)
    class UserDataUpdateFailedException(message: String): Throwable(message)
}
