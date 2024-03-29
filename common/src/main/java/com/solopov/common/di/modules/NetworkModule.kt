package com.solopov.common.di.modules

import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
    fun provideOkHttpClient(networkProperties: NetworkProperties): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(networkProperties.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(networkProperties.writeTimeout, TimeUnit.SECONDS)
            .readTimeout(networkProperties.readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideApiCreator(
        okHttpClient: OkHttpClient,
        appProperties: AppProperties,
    ): NetworkApiCreator {
        return NetworkApiCreator(okHttpClient, appProperties.getBaseUrl())
    }
}
