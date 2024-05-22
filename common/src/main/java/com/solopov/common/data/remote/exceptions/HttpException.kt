package com.solopov.common.data.remote.exceptions

sealed class HttpException {

    class InternalServerErrorException(message: String) : Throwable(message)
    class ServiceUnavailableException(message: String) : Throwable(message)
    class UnauthorizedException(message: String) : Throwable(message)

    class InvalidEmailException(message: String): Throwable(message)
    class NoSuchEmailException(message: String): Throwable(message)
    class NoEmptyPasswordException(message: String): Throwable(message)

    class EmailAlreadyInUseException(message: String): Throwable(message)

    class NoSuchUserException(message: String): Throwable(message)
    class WrongEmailOrPasswordException(message: String): Throwable(message)
}
