package com.solopov.feature_chat_api.domain.interfaces

import androidx.paging.PagingData
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun createNewMessage(userId: String, roomId: String, message: Message)
    suspend fun getCurrentUser(): User

    suspend fun downloadMessages(userId: String, roomId: String): List<Message>
    suspend fun getAllChatsByUserId(userId: String): List<Chat>
    suspend fun getRecentMessages(): Flow<PagingData<Message>>
}
