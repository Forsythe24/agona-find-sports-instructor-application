package com.solopov.feature_instructor_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.data.db.AppDatabase
import okhttp3.OkHttpClient

interface InstructorFeatureDependencies {

//    fun okHttpClient(): OkHttpClient

    fun networkApiCreator(): NetworkApiCreator

    fun db(): AppDatabase

    fun resourceManager(): ResourceManager
}
