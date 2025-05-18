package com.solopov.feature_chat_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.usecase.LoadChatMessagesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadChatMessagesUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): LoadChatMessagesUseCase {
    override suspend fun invoke(roomId: String): List<Message> {
        return withContext(ioDispatcher) {
            chatRepository.downloadMessages(roomId)
        }
    }
}
