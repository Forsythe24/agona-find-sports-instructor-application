package com.solopov.common.data.network.exceptions

sealed class AuthException(message: String): Exception(message) {
    class InvalidEmailException(message: String) : AuthException(message)
    class NoSuchEmailException(message: String) : AuthException(message)
    class NoEmptyPasswordException(message: String) : AuthException(message)
    class EmailAlreadyInUseException(message: String) : AuthException(message)
    class WrongPasswordException(message: String) : AuthException(message)
    class FailedToAuthorizeException(message: String): AuthException(message)
}
