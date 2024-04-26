package com.solopov.feature_instructor_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import okhttp3.OkHttpClient

interface InstructorFeatureDependencies {

//    fun okHttpClient(): OkHttpClient

    fun networkApiCreator(): NetworkApiCreator
    fun userFirebaseDao(): UserFirebaseDao

    fun db(): AppDatabase

    fun resourceManager(): ResourceManager
}
