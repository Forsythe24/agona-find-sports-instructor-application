package com.solopov.common.data.firebase.exceptions

sealed class AuthenticationException {

    class WeakPasswordException(message: String): Throwable(message)
    class InvalidEmailException(message: String): Throwable(message)
    class NoSuchEmailException(message: String): Throwable(message)
    class NoEmptyPasswordException(message: String): Throwable(message)

    class EmailAlreadyInUseException(message: String): Throwable(message)

    class NoSuchUserException(message: String): Throwable(message)
    class WrongEmailOrPasswordException(message: String): Throwable(message)
}