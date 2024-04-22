package com.solopov.feature_instructor_impl.data.mappers

import com.solopov.common.model.UserCommon
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorData
import com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter
import javax.inject.Inject
import kotlin.math.exp

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

    fun mapInstructorListItemToUserCommon(instructor: InstructorsAdapter.ListItem): UserCommon {
        return with(instructor) {
            UserCommon(
                id = id,
                name = name,
                age = age,
                gender = gender,
                sport = sport,
                photo = photo,
                experience = experience,
                description = description,
                rating = rating,
                hourlyRate = hourlyRate,
            )
        }
    }
    companion object {
        val sportTypes: List<String> = listOf("football", "tennis", "basketball", "hockey", "volleyball", "handball")
    }
}

