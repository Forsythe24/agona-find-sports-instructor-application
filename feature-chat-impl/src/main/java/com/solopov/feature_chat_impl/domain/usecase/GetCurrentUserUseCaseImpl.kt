package com.solopov.feature_chat_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_api.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GetCurrentUserUseCase {
    override suspend fun invoke(): User {
        return withContext(dispatcher) {
            chatRepository.getCurrentUser()
        }
    }
}
