package com.solopov.common.data.network.exceptions

sealed class HttpException {

    class InternalServerErrorException(message: String) : Throwable(message)
    class ServiceUnavailableException(message: String) : Throwable(message)
    class UnauthorizedException(message: String) : Throwable(message)
}
