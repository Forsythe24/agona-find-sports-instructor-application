package com.solopov.feature_chat_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.usecase.CreateNewMessageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateNewMessageUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CreateNewMessageUseCase {
    override suspend fun invoke(userId: String, message: Message) {
        return withContext(ioDispatcher) {
            chatRepository.createNewMessage(userId, message)
        }
    }
}
