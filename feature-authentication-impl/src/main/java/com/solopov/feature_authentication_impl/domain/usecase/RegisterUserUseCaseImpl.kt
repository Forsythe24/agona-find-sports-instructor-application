package com.solopov.feature_authentication_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_authentication_api.domain.AuthRepository
import com.solopov.feature_authentication_api.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : RegisterUserUseCase {
    override suspend fun invoke(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String
    ) {
        return withContext(ioDispatcher) {
            authRepository.createUser(
                email,
                password,
                name,
                age,
                gender
            )
        }
    }
}
