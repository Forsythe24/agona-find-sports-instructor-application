package com.solopov.feature_chat_api.domain.interfaces

import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User

interface ChatRepository {

    suspend fun createNewMessage(roomId: String, message: Message)
    suspend fun getCurrentUser(): User

    suspend fun downloadMessages(roomId: String): List<Message>
}
