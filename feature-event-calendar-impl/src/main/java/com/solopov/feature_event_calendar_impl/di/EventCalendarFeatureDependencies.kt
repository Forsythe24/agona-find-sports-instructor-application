package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.dao.ChatRemoteDao
import com.solopov.common.data.network.dao.UserRemoteDao

interface EventCalendarFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun userRemoteDao(): UserRemoteDao
    fun chatRemoteDao(): ChatRemoteDao
    fun db(): AppDatabase

}
