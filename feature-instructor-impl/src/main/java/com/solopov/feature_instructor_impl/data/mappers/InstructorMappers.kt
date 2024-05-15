package com.solopov.feature_instructor_impl.data.mappers

import com.solopov.common.data.remote.model.UserRemote
import com.solopov.feature_instructor_api.domain.model.Instructor
import javax.inject.Inject

class InstructorMappers @Inject constructor() {

    fun mapUserRemoteToInstructor(userRemote: UserRemote): Instructor {
        return with(userRemote) {
            Instructor(
                id = id,
                name = name,
                age = age,
                gender = gender,
                sport = sportName ?: "Football",
                photo = photo?: "",
                experience = experience?: "",
                description = description?: "",
                rating = rating?: 0f,
                numberOfRatings = numberOfRatings?: 0,
                hourlyRate = hourlyRate?: 0f,
            )
        }
    }
}

