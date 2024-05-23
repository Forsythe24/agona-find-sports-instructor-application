package com.solopov.feature_chat_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_api.domain.interfaces.ChatRepository
import com.solopov.feature_chat_impl.data.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ChatFeatureModule {
    @Provides
    @FeatureScope
    fun provideChatRepository(chatRepository: ChatRepositoryImpl): ChatRepository = chatRepository


    @Provides
    @FeatureScope
    fun provideChatInteractor(chatRepository: ChatRepository): ChatInteractor =
        ChatInteractor(chatRepository, Dispatchers.IO)
}
