package com.solopov.common.data.network.di

import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.data.network.api.AuthService
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.RefreshTokenService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.di.qualifier.AuthenticatedClient
import com.solopov.common.data.network.jwt.JwtManager
import okhttp3.OkHttpClient

interface RemoteApi {
    fun provideJwtManager(): JwtManager

    @AuthenticatedClient
    fun provideOkHttpClient(): OkHttpClient
    fun provideUserServiceApi(): UserApiService
    fun provideRefreshTokenService(): RefreshTokenService
    fun provideChatApiService(): ChatApiService
    fun provideAuthService(): AuthService
    fun provideFirebaseStorage(): FirebaseStorage
}
