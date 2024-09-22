package com.solopov.common.data.network.exceptions

sealed class UserException(message: String): Exception(message) {
    class UserNotFoundException(message: String) : UserException(message)
    class UserDataUpdateFailedException(message: String) : UserException(message)
    class UserNotCreatedException(message: String) : UserException(message)
}
