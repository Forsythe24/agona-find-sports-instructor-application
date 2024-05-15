package com.solopov.common.data.remote.di

import android.content.Context
import com.solopov.common.core.config.NetworkProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator
import okhttp3.OkHttpClient

interface FirebaseDependencies {
    fun context(): Context
    fun networkApiCreator(): NetworkApiCreator
    fun resourceManager(): ResourceManager

    fun networkProperties(): NetworkProperties
}
