package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.usecase.VerifyCredentialsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerifyCredentialsUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val dispatcher: CoroutineDispatcher,
) : VerifyCredentialsUseCase {
    override suspend fun invoke(password: String): Boolean {
        return withContext(dispatcher) {
            userProfileRepository.verifyCredentials(password)
        }
    }
}
