package com.solopov.common.di.modules

import android.content.Context
import android.net.ConnectivityManager
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.data.network.di.qualifier.PublicClient
import com.solopov.common.data.network.utils.NetworkApiCreator
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.data.network.utils.NetworkStateProviderImpl
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideNetworkProperties(appProperties: AppProperties): NetworkProperties {
        return appProperties.networkProperties()
    }

    @Provides
    @ApplicationScope
    fun provideConnectivityManager(
        context: Context,
    ) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @PublicClient
    @ApplicationScope
    fun provideUnauthenticatedOkHttpClient(networkProperties: NetworkProperties): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(networkProperties.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(networkProperties.writeTimeout, TimeUnit.SECONDS)
            .readTimeout(networkProperties.readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideUnauthenticatedApiCreator(
        @PublicClient
        okHttpClient: OkHttpClient,
        appProperties: AppProperties,
    ): NetworkApiCreator {
        return NetworkApiCreator(okHttpClient, appProperties.getBaseUrl())
    }

    @Provides
    @ApplicationScope
    fun provideNetworkStateProvider(
        connectivityManager: ConnectivityManager,
        scope: CoroutineScope,
    ): NetworkStateProvider {
        return NetworkStateProviderImpl(connectivityManager, scope)
    }
}
