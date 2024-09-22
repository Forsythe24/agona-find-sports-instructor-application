package com.solopov.common.data.network.jwt

import com.solopov.common.data.network.model.AuthNetworkResponse

interface JwtManager {
    suspend fun saveAccessJwt(token: String)
    suspend fun saveRefreshJwt(token: String)
    suspend fun getAccessJwt(): String?
    suspend fun getRefreshJwt(): String?
    suspend fun clearAllTokens()
}

suspend fun JwtManager.saveTokensFromResponse(authNetworkResponse: AuthNetworkResponse) {
    authNetworkResponse.apply {
        saveAccessJwt(accessToken)
        saveRefreshJwt(refreshToken)
    }
}
