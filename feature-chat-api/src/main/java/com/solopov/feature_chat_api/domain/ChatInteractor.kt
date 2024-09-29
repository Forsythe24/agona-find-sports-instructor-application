package com.solopov.feature_chat_api.domain

import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun createNewMessage(userId: String, message: Message) {
        return withContext(ioDispatcher) {
            chatRepository.createNewMessage(userId, message)
        }
    }

    suspend fun getCurrentUser(): User {
        return withContext(ioDispatcher) {
            chatRepository.getCurrentUser()
        }
    }

    suspend fun downloadMessages(roomId: String): List<Message> {
        return withContext(ioDispatcher) {
            chatRepository.downloadMessages(roomId)
        }
    }

    suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        return withContext(ioDispatcher) {
            chatRepository.getAllChatsByUserId(userId)
        }
    }
}
