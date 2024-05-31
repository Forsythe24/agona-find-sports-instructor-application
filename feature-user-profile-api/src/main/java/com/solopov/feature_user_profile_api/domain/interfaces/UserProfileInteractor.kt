package com.solopov.feature_user_profile_api.domain.interfaces

import com.solopov.feature_user_profile_api.domain.model.Rating
import com.solopov.feature_user_profile_api.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class  UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository,
    private val ratingRepository: RatingRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getUserByUid(uid: String): User {
        return withContext(dispatcher) {
            userProfileRepository.getUserByUid(uid)
        }
    }

    suspend fun getCurrentUser(): User {
        return withContext(dispatcher) {
            userProfileRepository.getCurrentUser()
        }
    }

    suspend fun updateUser(user: User) {
        return withContext(dispatcher) {
            userProfileRepository.updateUser(user)
        }
    }

    suspend fun updateUserPassword(password: String) {
        return withContext(dispatcher) {
            userProfileRepository.updateUserPassword(password)
        }
    }

    suspend fun verifyCredentials(password: String): Boolean {
        return withContext(dispatcher) {
            userProfileRepository.verifyCredentials(password)
        }
    }

    suspend fun uploadProfileImage(imageUri: String): String {
        return withContext(dispatcher) {
            userProfileRepository.uploadProfileImage(imageUri)
        }
    }

    suspend fun addRating(rating: Rating) {
        return withContext(dispatcher) {
            ratingRepository.addRating(rating)
        }
    }

    suspend fun getAllInstructorRatingsById(instructorId: String): List<Rating>? {
        return withContext(dispatcher) {
            ratingRepository.getAllInstructorRatingsById(instructorId)
        }
    }

    suspend fun getRatingByUserAndInstructorIds(userId: String, instructorId: String): Rating? {
        return withContext(dispatcher) {
            ratingRepository.getRatingByUserAndInstructorIds(userId, instructorId)
        }
    }

    suspend fun deleteProfile(): Boolean {
        return withContext(dispatcher) {
            userProfileRepository.deleteProfile()
        }
    }

}
