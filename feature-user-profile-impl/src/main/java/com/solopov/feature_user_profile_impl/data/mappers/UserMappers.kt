package com.solopov.feature_user_profile_impl.data.mappers

import com.solopov.common.data.remote.model.UserRemote
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import javax.inject.Inject

class UserMappers @Inject constructor() {

    fun mapUserRemoteToUser(userRemote: UserRemote): User {
        return with(userRemote) {
            User(
                userRemote.id,
                name,
                age,
                gender,
                sportName,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapUserToUserRemote(user: User): UserRemote {
        return with(user) {
            UserRemote(
                user.id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapUserToUserProfile(user: User): UserProfile {
        return with(user) {
            UserProfile(
                id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapUserProfileToUser(userProfile: UserProfile): User {
        return with(userProfile) {
            User(
                id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

}
