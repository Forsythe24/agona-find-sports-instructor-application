package com.solopov.feature_authentication_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.dao.UserRemoteDao
import com.solopov.common.data.storage.UserDataStore

interface AuthFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userRemoteDao(): UserRemoteDao

    fun userDataStore(): UserDataStore
}
