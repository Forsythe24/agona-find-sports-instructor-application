package com.solopov.feature_chat_impl.di

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.dao.ChatFirebaseDao
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.common.utils.DateFormatter

interface ChatFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun userFirebaseDao(): UserFirebaseDao
    fun chatFirebaseDao(): ChatFirebaseDao
    fun dateFormatter(): DateFormatter
}
