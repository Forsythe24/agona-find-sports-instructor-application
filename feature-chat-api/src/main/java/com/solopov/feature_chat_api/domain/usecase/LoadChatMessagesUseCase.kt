package com.solopov.feature_chat_api.domain.usecase

import com.solopov.feature_chat_api.domain.model.Message

interface LoadChatMessagesUseCase {
    suspend operator fun invoke(roomId: String): List<Message>
}
