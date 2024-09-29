package com.solopov.feature_chat_api.di

import com.solopov.feature_chat_api.domain.ChatInteractor
import com.solopov.feature_chat_api.domain.ChatRepository

interface ChatFeatureApi {
    fun provideChatInteractor(): ChatInteractor
    fun provideChatRepository(): ChatRepository
}
