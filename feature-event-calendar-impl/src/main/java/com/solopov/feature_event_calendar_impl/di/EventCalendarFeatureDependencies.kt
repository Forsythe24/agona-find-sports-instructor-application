package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService

interface EventCalendarFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun db(): AppDatabase
    fun userApiService(): UserApiService
    fun chatApiService(): ChatApiService
}
