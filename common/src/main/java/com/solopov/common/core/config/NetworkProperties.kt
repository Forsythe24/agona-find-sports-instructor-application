package com.solopov.common.core.config

data class NetworkProperties(
    val connectTimeout: Long,
    val readTimeout: Long,
    val writeTimeout: Long
)
