package com.solopov.feature_user_profile_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.dao.UserFirebaseDao

interface UserProfileFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userFirebaseDao(): UserFirebaseDao
}
