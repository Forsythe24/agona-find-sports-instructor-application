package com.solopov.common.data.network.exceptions

sealed class HttpException(message: String? = "Exception occurred during making an http request") : Exception(message) {
    class UnauthorizedException(message: String? = "Failed to access the resource due to lack of authority") : HttpException(message)

    class ServerFailedException(
        message: String? = "The server encountered an unexpected condition that prevented it from fulfilling the request",
    ) : HttpException(message)

    class ClientException(
        message: String? = "The request for the resource contains bad syntax or cannot be filled for some other reason",
    ) : HttpException(message)
}
