package com.solopov.feature_chat_api.di

import com.solopov.feature_chat_api.domain.ChatRepository

interface ChatFeatureApi {
    fun provideChatRepository(): ChatRepository
}
