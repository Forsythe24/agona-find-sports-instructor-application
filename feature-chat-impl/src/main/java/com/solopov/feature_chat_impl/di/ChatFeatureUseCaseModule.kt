package com.solopov.feature_chat_impl.di

import com.solopov.feature_chat_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadAllUserChatsUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadChatMessagesUseCase
import com.solopov.feature_chat_api.domain.usecase.SendMessageUseCase
import com.solopov.feature_chat_impl.domain.usecase.GetCurrentUserUseCaseImpl
import com.solopov.feature_chat_impl.domain.usecase.LoadAllUserChatsUseCaseImpl
import com.solopov.feature_chat_impl.domain.usecase.LoadChatMessagesUseCaseImpl
import com.solopov.feature_chat_impl.domain.usecase.SendMessageUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface ChatFeatureUseCaseModule {
    @Binds
    fun bindSendNewMessageUseCase(impl: SendMessageUseCaseImpl): SendMessageUseCase

    @Binds
    fun bindLoadAllUserChatsUseCase(impl: LoadAllUserChatsUseCaseImpl): LoadAllUserChatsUseCase

    @Binds
    fun bindLoadChatMessagesUseCase(impl: LoadChatMessagesUseCaseImpl): LoadChatMessagesUseCase

    @Binds
    fun bindGetCurrentUserUseCase(impl: GetCurrentUserUseCaseImpl): GetCurrentUserUseCase
}
