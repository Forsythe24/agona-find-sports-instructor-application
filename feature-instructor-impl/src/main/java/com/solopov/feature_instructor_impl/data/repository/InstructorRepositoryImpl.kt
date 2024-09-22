package com.solopov.feature_instructor_impl.data.repository

import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.utils.handleApiErrors
import com.solopov.common.data.network.utils.makeSafeApiCall
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import com.solopov.feature_instructor_impl.data.network.InstructorsApiService
import java.net.HttpURLConnection
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val instructorMappers: InstructorMappers,
    private val apiService: InstructorsApiService,
) : InstructorRepository {

    override suspend fun getInstructorsBySportId(sportId: Int): List<Instructor> {
        val result = makeSafeApiCall {
            apiService.getInstructorsBySportId(sportId + ID_OFFSET)
        }
        return result.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                    "Failed to download notifications due to lack of authority",
                )
            )
        ).map(instructorMappers::mapUserRemoteToInstructor)
    }

    companion object {
        const val ID_OFFSET = 1
    }
}
