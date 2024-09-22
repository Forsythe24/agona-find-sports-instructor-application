package com.solopov.common.data.network.api

import com.solopov.common.data.network.model.AuthNetworkResponse
import com.solopov.common.data.network.model.RefreshJwtRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body requestDto: RefreshJwtRequestDto
    ): Response<AuthNetworkResponse>
}
