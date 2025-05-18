package com.solopov.feature_chat_api.domain.usecase

import com.solopov.feature_chat_api.domain.model.Chat

interface LoadAllUserChatsUseCase {
    suspend operator fun invoke(userId: String): List<Chat>
}
