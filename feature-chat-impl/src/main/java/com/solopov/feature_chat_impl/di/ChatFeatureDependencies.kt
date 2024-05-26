package com.solopov.feature_chat_impl.di

import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.dao.ChatRemoteDao
import com.solopov.common.data.remote.dao.UserRemoteDao
import com.solopov.common.data.remote.jwt.JwtManager
import com.solopov.common.utils.DateFormatter

interface ChatFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun jwtManager(): JwtManager
    fun appProperties(): AppProperties
    fun userRemoteDao(): UserRemoteDao
    fun chatFirebaseDao(): ChatRemoteDao
    fun dateFormatter(): DateFormatter
}
