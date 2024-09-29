package com.solopov.feature_chat_impl.di

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_chat_api.domain.ChatInteractor
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_impl.data.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class ChatFeatureModule {
    @Provides
    @FeatureScope
    fun provideChatRepository(chatRepository: ChatRepositoryImpl): ChatRepository = chatRepository


    @Provides
    @FeatureScope
    fun provideChatInteractor(chatRepository: ChatRepository, @IoDispatcher ioDispatcher: CoroutineDispatcher): ChatInteractor =
        ChatInteractor(chatRepository, ioDispatcher)
}
