package com.solopov.common.data.network.model

data class AuthNetworkResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String?,
)

