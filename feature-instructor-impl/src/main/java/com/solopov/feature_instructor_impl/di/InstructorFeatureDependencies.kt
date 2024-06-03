package com.solopov.feature_instructor_impl.di

import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.dao.UserRemoteDao

interface InstructorFeatureDependencies {
    fun userRemoteDao(): UserRemoteDao

    fun db(): AppDatabase

    fun resourceManager(): ResourceManager
}
