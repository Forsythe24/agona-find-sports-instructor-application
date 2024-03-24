package com.solopov.feature_instructor_impl.data.mappers

import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorData
import javax.inject.Inject

class InstructorMappers @Inject constructor() {

    fun mapInstructorDataToInstructor(instructorData: InstructorData): Instructor {
        return with(instructorData) {
            Instructor(
                id = id.toString(),
                name = name?: "",
                age = age?: 1,
                gender = genderData?.gender?: "M",
                sport = sportTypes[((sportId)?: 1) - 1],
                photo = photo?: "",
                experience = "",
                description = "",
                rating = (rating?: 0f) / 20,
                hourlyRate = 0f,
            )
        }
    }
    companion object {
        val sportTypes: List<String> = listOf("football", "tennis", "basketball", "hockey", "volleyball", "handball")
    }
}

