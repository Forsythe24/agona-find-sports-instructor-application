package com.solopov.feature_chat_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.usecase.LoadAllUserChatsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadAllUserChatsUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): LoadAllUserChatsUseCase {
    override suspend fun invoke(userId: String): List<Chat> {
        return withContext(ioDispatcher) {
            chatRepository.getAllChatsByUserId(userId)
        }
    }
}
