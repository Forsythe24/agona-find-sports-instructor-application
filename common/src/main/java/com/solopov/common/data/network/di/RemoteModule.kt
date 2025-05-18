package com.solopov.common.data.network.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.data.network.api.AuthService
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.RefreshTokenService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.di.qualifier.AuthenticatedClient
import com.solopov.common.data.network.di.qualifier.TokenRefreshClient
import com.solopov.common.data.network.interceptor.AccessTokenInterceptor
import com.solopov.common.data.network.interceptor.RefreshTokenInterceptor
import com.solopov.common.data.network.jwt.JwtAuthenticator
import com.solopov.common.data.network.jwt.JwtDataStore
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.utils.NetworkApiCreator
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {

    @ApplicationScope
    @Provides
    fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @ApplicationScope
    @Provides
    fun provideUserApiService(
        @AuthenticatedClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator,
    ): UserApiService {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(UserApiService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideAuthService(
        apiCreator: NetworkApiCreator,
    ): AuthService {
        return apiCreator.create(AuthService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideRefreshTokenService(
        @TokenRefreshClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator,
    ): RefreshTokenService {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(RefreshTokenService::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideChatApiService(
        @AuthenticatedClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator,
    ): ChatApiService {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(ChatApiService::class.java)
    }

    @Provides
    @AuthenticatedClient
    @ApplicationScope
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authenticator: JwtAuthenticator,
        networkProperties: NetworkProperties,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(networkProperties.connectTimeout, TimeUnit.SECONDS)
            .readTimeout(networkProperties.writeTimeout, TimeUnit.SECONDS)
            .writeTimeout(networkProperties.readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @TokenRefreshClient
    @ApplicationScope
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor,
        networkProperties: NetworkProperties,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(refreshTokenInterceptor)
            .connectTimeout(networkProperties.connectTimeout, TimeUnit.SECONDS)
            .readTimeout(networkProperties.writeTimeout, TimeUnit.SECONDS)
            .writeTimeout(networkProperties.readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideJwtManager(dataStore: DataStore<Preferences>): JwtManager {
        return JwtDataStore(dataStore = dataStore)
    }
}
