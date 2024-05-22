package com.solopov.common.data.remote.exceptions

sealed class AuthException {

    class WeakPasswordException(message: String): Throwable(message)
    class InvalidEmailException(message: String): Throwable(message)
    class NoSuchEmailException(message: String): Throwable(message)
    class NoEmptyPasswordException(message: String): Throwable(message)

    class EmailAlreadyInUseException(message: String): Throwable(message)

    class NoSuchUserException(message: String): Throwable(message)
    class WrongEmailOrPasswordException(message: String): Throwable(message)
    class WrongPasswordException(message: String): Throwable(message)
}
