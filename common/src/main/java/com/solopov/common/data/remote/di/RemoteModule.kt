package com.solopov.common.data.remote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.data.remote.AuthService
import com.solopov.common.data.remote.RefreshTokenService
import com.solopov.common.data.remote.SportApi
import com.solopov.common.data.remote.di.qualifier.AuthenticatedClient
import com.solopov.common.data.remote.di.qualifier.TokenRefreshClient
import com.solopov.common.data.remote.interceptor.AccessTokenInterceptor
import com.solopov.common.data.remote.interceptor.RefreshTokenInterceptor
import com.solopov.common.data.remote.jwt.JwtAuthenticator
import com.solopov.common.data.remote.jwt.JwtTokenDataStore
import com.solopov.common.data.remote.jwt.JwtTokenManager
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.utils.ParamsKey.AUTH_PREFERENCES_KEY
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {

    @ApplicationScope
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @ApplicationScope
    @Provides
    fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @ApplicationScope
    @Provides
    fun provideSportApi(
        @AuthenticatedClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator,
    ): SportApi {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(SportApi::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideAuthService(
        apiCreator: NetworkApiCreator
    ): AuthService {
        return apiCreator.create(AuthService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideRefreshTokenService(
        @TokenRefreshClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator
    ): RefreshTokenService {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(RefreshTokenService::class.java)
    }

    @Provides
    @AuthenticatedClient
    @ApplicationScope
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authenticator: JwtAuthenticator,
        networkProperties: NetworkProperties
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
        networkProperties: NetworkProperties
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
    fun provideDataStore(appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(AUTH_PREFERENCES_KEY) }
        )
    }

    @ApplicationScope
    @Provides
    fun provideJwtTokenManager(dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenDataStore(dataStore = dataStore)
    }
}
