package com.solopov.feature_instructor_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.di.qualifier.AuthenticatedClient
import com.solopov.common.data.network.utils.NetworkApiCreator
import okhttp3.OkHttpClient

interface InstructorFeatureDependencies {
    fun db(): AppDatabase

    fun resourceManager(): ResourceManager

    @AuthenticatedClient
    fun okHttpClient(): OkHttpClient

    fun apiCreator(): NetworkApiCreator
}
