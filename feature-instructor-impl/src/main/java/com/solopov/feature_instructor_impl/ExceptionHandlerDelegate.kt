package com.solopov.feature_instructor_impl

import com.solopov.common.data.network.exceptions.handler.ExceptionHandler
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor() : ExceptionHandler {
    override fun handleException(ex: Exception): Exception {
        return when (ex) {
            else -> {
                ex
            }
        }
    }
}
