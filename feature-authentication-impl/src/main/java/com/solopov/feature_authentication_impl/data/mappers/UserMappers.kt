package com.solopov.feature_authentication_impl.data.mappers

import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.feature_authentication_api.domain.model.User
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
                hourlyRate,
                isInstructor
            )
        }
    }

}
