package com.solopov.feature_authentication_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.api.AuthService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher

interface AuthFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userDataStore(): UserDataStore

    fun userApiService(): UserApiService

    fun authService(): AuthService

    fun jwtManager(): JwtManager

    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher

    fun networkStateProvider(): NetworkStateProvider
}
