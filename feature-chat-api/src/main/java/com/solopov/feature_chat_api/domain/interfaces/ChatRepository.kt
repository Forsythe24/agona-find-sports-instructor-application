package com.solopov.feature_chat_api.domain.interfaces

import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User

interface ChatRepository {

    suspend fun createNewMessage(userId: String, message: Message)
    suspend fun getCurrentUser(): User

    suspend fun downloadMessages(chatId: String): List<Message>
    suspend fun getAllChatsByUserId(userId: String): List<Chat>
}
