package com.solopov.feature_chat_api.domain.interfaces

import androidx.paging.PagingData
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun createNewMessage(userId: String, roomId: String, message: Message) {
        return withContext(dispatcher) {
            chatRepository.createNewMessage(userId, roomId, message)
        }
    }

    suspend fun getCurrentUser(): User {
        return withContext(dispatcher) {
            chatRepository.getCurrentUser()
        }
    }

    suspend fun downloadMessages(userId: String, roomId: String): List<Message> {
        return withContext(dispatcher) {
            chatRepository.downloadMessages(userId, roomId)
        }
    }

    suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        return withContext(dispatcher) {
            chatRepository.getAllChatsByUserId(userId)
        }
    }

    suspend fun getRecentMessages(): Flow<PagingData<Message>>{
        return withContext(dispatcher) {
            chatRepository.getRecentMessages()
        }
    }
}
