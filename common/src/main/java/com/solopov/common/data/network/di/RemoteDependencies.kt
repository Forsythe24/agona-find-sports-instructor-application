package com.solopov.common.data.network.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator

interface RemoteDependencies {
    fun context(): Context
    fun networkApiCreator(): NetworkApiCreator
    fun resourceManager(): ResourceManager
    fun dataStore(): DataStore<Preferences>
    fun networkProperties(): NetworkProperties
}
