package com.solopov.feature_chat_api.domain.usecase

import com.solopov.feature_chat_api.domain.model.Message

interface CreateNewMessageUseCase {
    suspend operator fun invoke(userId: String, message: Message)
}
