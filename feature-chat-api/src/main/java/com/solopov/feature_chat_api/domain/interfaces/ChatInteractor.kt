package com.solopov.feature_chat_api.domain.interfaces

import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun createNewMessage(roomId: String, message: Message) {
        return withContext(dispatcher) {
            chatRepository.createNewMessage(roomId, message)
        }
    }

    suspend fun getCurrentUser(): User {
        return withContext(dispatcher) {
            chatRepository.getCurrentUser()
        }
    }

    suspend fun downloadMessages(roomId: String): List<Message> {
        return withContext(dispatcher) {
            chatRepository.downloadMessages(roomId)
        }
    }
}
