package com.solopov.common.di

import android.content.Context
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator

interface CommonApi {

    fun applicationContext(): Context

    fun provideResourceManager(): ResourceManager

    fun provideNetworkApiCreator(): NetworkApiCreator

    fun provideAppProperties(): AppProperties
}
