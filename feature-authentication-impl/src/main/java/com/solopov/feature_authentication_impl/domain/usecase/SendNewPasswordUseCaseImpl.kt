package com.solopov.feature_authentication_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_authentication_api.domain.AuthRepository
import com.solopov.feature_authentication_api.domain.usecase.SendNewPasswordUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendNewPasswordUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SendNewPasswordUseCase {
    override suspend fun invoke(email: String) {
        return withContext(ioDispatcher) {
            authRepository.sendNewPassword(email)
        }
    }
}
