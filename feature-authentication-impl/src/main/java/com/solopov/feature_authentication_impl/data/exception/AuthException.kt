package com.solopov.feature_authentication_impl.data.exception

sealed class AuthException(message: String) : Exception(message) {
    class InvalidEmailException(message: String) : AuthException(message)
    class NoSuchEmailException(message: String) : AuthException(message)
    class NoEmptyPasswordException(message: String) : AuthException(message)
    class EmailAlreadyInUseException(message: String) : AuthException(message)
    class WrongPasswordException(message: String) : AuthException(message)
}
//sealed class ApiError(
//    @StringRes val defaultMessageRes: Int,
//    message: String? = null,
//    cause: Throwable? = null,
//) : Exception(message, cause) {
//
//    class FailedAuthorizationException(
//        message: String? = null,
//        cause: Throwable? = null,
//    ) : ApiError(R.string.error_invalid_token, message, cause)
