package com.solopov.feature_chat_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.dao.UserFirebaseDao

interface ChatFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userFirebaseDao(): UserFirebaseDao
}
