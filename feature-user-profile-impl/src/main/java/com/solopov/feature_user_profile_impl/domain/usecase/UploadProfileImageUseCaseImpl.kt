package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.usecase.UploadProfileImageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadProfileImageUseCaseImpl @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UploadProfileImageUseCase {
    override suspend fun invoke(imageUri: String): String {
        return withContext(dispatcher) {
            userProfileRepository.uploadProfileImage(imageUri)
        }
    }
}
