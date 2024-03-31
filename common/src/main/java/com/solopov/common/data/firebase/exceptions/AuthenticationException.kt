package com.solopov.common.data.firebase.exceptions

sealed class AuthenticationException {
    class SignUpException(message: String): Throwable(message)
    class SignInException(message: String): Throwable(message)
}
