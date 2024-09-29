package com.solopov.feature_instructor_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.di.qualifier.AuthenticatedClient
import com.solopov.common.data.network.utils.NetworkApiCreator
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient

interface InstructorFeatureDependencies {
    fun db(): AppDatabase

    fun resourceManager(): ResourceManager

    @AuthenticatedClient
    fun okHttpClient(): OkHttpClient

    fun apiCreator(): NetworkApiCreator

    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher

    fun networkStateProvider(): NetworkStateProvider

}
