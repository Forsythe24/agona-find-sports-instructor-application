package com.solopov.feature_user_profile_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.dao.UserRemoteDao

interface UserProfileFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userRemoteDao(): UserRemoteDao
    fun db(): AppDatabase

}
