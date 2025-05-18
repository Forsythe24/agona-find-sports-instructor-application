package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_api.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GetCurrentUserUseCase {
    override suspend fun invoke(): User {
        return withContext(dispatcher) {
            userProfileRepository.getCurrentUser()
        }
    }
}
