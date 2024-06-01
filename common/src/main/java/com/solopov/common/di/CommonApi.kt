package com.solopov.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.DateFormatter

interface CommonApi {

    fun applicationContext(): Context

    fun provideResourceManager(): ResourceManager

    fun provideNetworkApiCreator(): NetworkApiCreator

    fun provideNetworkProperties(): NetworkProperties
    fun provideUserDataStore(): UserDataStore
    fun provideDataStore(): DataStore<Preferences>
    fun provideAppProperties(): AppProperties
    fun provideDateFormatter(): DateFormatter
}
