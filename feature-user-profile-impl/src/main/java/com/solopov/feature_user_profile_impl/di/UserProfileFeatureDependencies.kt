package com.solopov.feature_user_profile_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.db.dao.RatingDao
import com.solopov.common.data.firebase.dao.UserFirebaseDao

interface UserProfileFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userFirebaseDao(): UserFirebaseDao
    fun db(): AppDatabase

}
