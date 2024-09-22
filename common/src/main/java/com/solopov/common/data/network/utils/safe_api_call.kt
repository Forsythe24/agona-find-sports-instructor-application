package com.solopov.common.data.network.utils

import android.util.Log
import com.solopov.common.data.network.exceptions.DataLoadingException
import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.exceptions.NetworkException
import retrofit2.Response
import java.io.IOException

suspend fun <T> makeSafeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ResultWrapper.Success(it)
            } ?: ResultWrapper.GenericError(response.code(), "Response body is null")
        } else {
            val code = response.code()
            val errorResponse = response.errorBody()?.string()
            ResultWrapper.GenericError(code, errorResponse)
        }
    } catch (ex: Exception) {
        Log.e("ApiCall", "Exception during API call", ex)
        when (ex) {
            is IOException -> ResultWrapper.NetworkError
            is retrofit2.HttpException -> {
                val code = ex.code()
                val errorResponse = ex.response()?.errorBody()?.string()
                ResultWrapper.GenericError(code, errorResponse)
            }

            else -> ResultWrapper.GenericError(null, ex.localizedMessage)
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
}

fun <T> ResultWrapper<T>.handleApiErrors(
    errorMappings: Map<Int, Exception> = emptyMap(),
): T {
    when (this) {
        is ResultWrapper.GenericError -> {
            when (this.code) {
                in errorMappings.keys -> {
                    throw errorMappings[this.code]!!
                }
                in HttpValues.clientErrorRange -> throw HttpException.ClientException()
                in HttpValues.serverErrorRange -> throw HttpException.ServerFailedException()
                else -> {
                    throw DataLoadingException()
                }
            }
        }

        is ResultWrapper.NetworkError -> throw NetworkException("Network failed")
        is ResultWrapper.Success -> return this.value
    }
}

