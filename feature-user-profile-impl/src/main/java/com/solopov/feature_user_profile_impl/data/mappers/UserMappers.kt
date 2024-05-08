package com.solopov.feature_user_profile_impl.data.mappers

import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.model.ChatCommon
import com.solopov.common.model.UserCommon
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import javax.inject.Inject

class UserMappers @Inject constructor() {

    fun mapUserFirebaseToUser(userFirebase: UserFirebase): User {
        return with(userFirebase) {
            User(
                userFirebase.id,
                email,
                password,
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

    fun mapUserCommonToChatCommon(userCommon: UserCommon): ChatCommon {
        return with(userCommon) {
            ChatCommon(
                id,
                name,
                photo
            )
        }
    }

    fun mapUserToUserFirebase(user: User): UserFirebase {
        return with(user) {
            UserFirebase(
                user.id,
                email,
                password,
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

    fun mapUserProfileToUserCommon(user: UserProfile): UserCommon {
        return with(user) {
            UserCommon(
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
                email,
                password,
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
                email!!,
                password!!,
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

    fun mapUserProfileToUserForRating(userProfile: UserProfile): User {
        return with(userProfile) {
            User(
                id,
                "",
                "",
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


    fun mapUserCommonToUserProfile(userCommon: UserCommon): UserProfile {
        return with(userCommon) {
            UserProfile(
                id = id,
                email = null,
                password = null,
                name = name,
                age = age,
                gender = gender,
                sport = sport,
                photo = photo,
                experience = experience,
                description = description,
                rating = rating,
                numberOfRatings = numberOfRatings,
                hourlyRate = hourlyRate,
                isInstructor = isInstructor
            )
        }
    }

}
