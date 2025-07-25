package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.usecase.DeleteProfileUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProfileUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : DeleteProfileUseCase {
    override suspend fun invoke(): Boolean {
        return withContext(dispatcher) {
            userProfileRepository.deleteProfile()
        }
    }
}
