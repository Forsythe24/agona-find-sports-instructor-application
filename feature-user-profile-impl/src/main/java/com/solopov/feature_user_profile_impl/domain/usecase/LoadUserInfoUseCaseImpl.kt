package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_api.domain.usecase.LoadUserInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadUserInfoUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : LoadUserInfoUseCase {
    override suspend fun invoke(userId: String): User {
        return withContext(dispatcher) {
            userProfileRepository.getUserById(userId)
        }
    }
}
