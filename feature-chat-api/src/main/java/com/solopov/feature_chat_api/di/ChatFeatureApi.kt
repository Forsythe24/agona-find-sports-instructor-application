package com.solopov.feature_chat_api.di

import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_api.domain.interfaces.ChatRepository

interface ChatFeatureApi {
    fun provideChatInteractor(): ChatInteractor
    fun provideChatRepository(): ChatRepository
}
