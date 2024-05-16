package com.solopov.common.data.remote

import com.solopov.common.data.remote.model.AuthNetworkResponse
import com.solopov.common.data.remote.model.CredentialsRemote
import com.solopov.common.data.remote.model.UserRemote
import com.solopov.common.data.remote.model.UserSignUpRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Body credentials: CredentialsRemote
    ): Response<AuthNetworkResponse>

    @POST("auth/sign_up")
    suspend fun createUser (
        @Body user: UserSignUpRemote
    ): UserRemote
}