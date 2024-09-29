package com.solopov.common.data.network.exceptions.handler

interface ExceptionHandler {
    fun handleException(ex: Exception): Exception
}
