package com.solopov.common.data.network.exceptions

sealed class UserException {
    class UserNotFound(message: String) : Throwable(message)
    class UserDataUpdateFailedException(message: String) : Throwable(message)
    class UserNotCreatedException(message: String) : Throwable(message)
}
