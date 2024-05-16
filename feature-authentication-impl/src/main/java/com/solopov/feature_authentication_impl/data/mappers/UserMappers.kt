package com.solopov.feature_authentication_impl.data.mappers

import com.solopov.common.data.remote.model.UserRemote
import com.solopov.feature_authentication_api.domain.model.User
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
                hourlyRate,
                isInstructor
            )
        }
    }

}
