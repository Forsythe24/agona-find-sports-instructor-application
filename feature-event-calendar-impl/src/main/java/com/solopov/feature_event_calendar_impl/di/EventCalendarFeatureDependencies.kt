package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.remote.dao.UserRemoteDao

interface EventCalendarFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userRemoteDao(): UserRemoteDao
    fun db(): AppDatabase

}