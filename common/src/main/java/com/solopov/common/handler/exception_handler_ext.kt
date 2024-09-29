package com.solopov.common.handler

import com.solopov.common.data.network.exceptions.handler.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchWithHandler(
    exceptionHandlerDelegate: ExceptionHandlerDelegate,
    onError: FlowCollector<T>.(Throwable) -> Unit,
): Flow<T> = catch { ex ->
    exceptionHandlerDelegate.handleException(ex)
    onError(ex)
}

inline fun <T, R> T.runCatching(
    exceptionHandlerDelegate: ExceptionHandler,
    block: T.() -> R,
): Result<R> {
    return try {
        Result.success(block())
    } catch (ex: Exception) {
        Result.failure(exceptionHandlerDelegate.handleException(ex))
    }
}
