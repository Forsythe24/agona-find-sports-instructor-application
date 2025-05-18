package com.solopov.feature_chat_api.domain.usecase

import com.solopov.feature_chat_api.domain.model.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): User
}
