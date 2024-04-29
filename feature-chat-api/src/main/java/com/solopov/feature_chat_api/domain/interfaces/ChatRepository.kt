package com.solopov.feature_chat_api.domain.interfaces

import androidx.paging.PagingData
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun createNewMessage(roomId: String, message: Message)
    suspend fun getCurrentUser(): User

    suspend fun downloadMessages(roomId: String): List<Message>
    suspend fun getRecentMessages(): Flow<PagingData<Message>>
}
