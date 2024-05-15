package com.solopov.feature_authentication_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.dao.UserRemoteDao

interface AuthFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userRemoteDao(): UserRemoteDao
}
