package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher

interface EventCalendarFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun db(): AppDatabase
    fun userApiService(): UserApiService
    fun chatApiService(): ChatApiService
    fun networkStateProvider(): NetworkStateProvider

    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher
}
