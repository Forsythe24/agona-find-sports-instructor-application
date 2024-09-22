package com.solopov.feature_chat_impl.di

import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.utils.DateFormatter

interface ChatFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun jwtManager(): JwtManager
    fun appProperties(): AppProperties
    fun dateFormatter(): DateFormatter
    fun userApiService(): UserApiService
    fun chatApiService(): ChatApiService
}
