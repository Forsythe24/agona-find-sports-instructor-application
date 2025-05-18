package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserPasswordUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserPasswordUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UpdateUserPasswordUseCase {
    override suspend fun invoke(password: String) {
        return withContext(dispatcher) {
            userProfileRepository.updateUserPassword(password)
        }
    }
}
