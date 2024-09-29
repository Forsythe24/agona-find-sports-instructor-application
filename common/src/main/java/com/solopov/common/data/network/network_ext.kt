package com.solopov.common.data.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.solopov.common.data.network.utils.NetworkStateProvider
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

suspend inline fun <T> makeSafeApiCall(networkStateProvider: NetworkStateProvider, noinline apiCall: suspend () -> Response<T>): T {
    val wrappedResult = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ResultWrapper.Success(it)
            } ?: ResultWrapper.GenericError(response.code(), "Response body is null")
        } else {
            val code = response.code()
            val errorResponse = parseErrorResponse(response.errorBody()?.string())
            ResultWrapper.GenericError(code, errorResponse?.message ?: "Unknown error")
        }
    } catch (ex: Exception) {
        Log.e("ApiCall", "Exception during API call", ex)
        when (ex) {
            is IOException -> ResultWrapper.NetworkError
            is retrofit2.HttpException -> {
                val code = ex.code()
                val errorResponse = parseErrorResponse(ex.response()?.errorBody()?.string())
                ResultWrapper.GenericError(code, errorResponse?.message ?: "Unknown error")
            }

            else -> ResultWrapper.GenericError(null, ex.localizedMessage)
        }
    }
    return wrappedResult.processResult(networkStateProvider)
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val message: String? = null) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
}

fun <T> ResultWrapper<T>.processResult(
    networkStateProvider: NetworkStateProvider,
): T {
    if (!networkStateProvider.isNetworkAvailable) {
        throw ApiError.NoInternetException()
    }
    when (this) {
        is ResultWrapper.GenericError -> {
            throw when (this.code) {
                HttpURLConnection.HTTP_BAD_REQUEST,
                HttpURLConnection.HTTP_FORBIDDEN,
                -> ApiError.HostException(message = this.message)

                HttpURLConnection.HTTP_NOT_FOUND -> ApiError.NotFoundException()

                HttpURLConnection.HTTP_UNAUTHORIZED -> ApiError.FailedAuthorizationException()

                HttpURLConnection.HTTP_UNAVAILABLE,
                HttpURLConnection.HTTP_INTERNAL_ERROR,
                HttpURLConnection.HTTP_BAD_GATEWAY,
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
                -> ApiError.ServerException(message = this.message)

                HttpURLConnection.HTTP_CONFLICT -> ApiError.ConflictException()

                else -> ApiError.UnknownException()
            }
        }

        is ResultWrapper.NetworkError -> throw ApiError.HostException()
        is ResultWrapper.Success -> return this.value
    }
}

fun parseErrorResponse(errorBody: String?): ErrorDto? {
    return try {
        Gson().fromJson(errorBody, ErrorDto::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}
