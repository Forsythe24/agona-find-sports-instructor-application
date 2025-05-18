package com.solopov.feature_authentication_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_authentication_api.domain.AuthRepository
import com.solopov.feature_authentication_api.domain.usecase.SignInUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SignInUserUseCase {
    override suspend fun invoke(email: String, password: String): Boolean {
        return withContext(ioDispatcher) {
            authRepository.signInUser(email, password)
        }
    }
}
