package com.solopov.feature_instructor_impl.data.repository

import com.solopov.common.data.network.makeSafeApiCall
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.feature_instructor_api.domain.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import com.solopov.feature_instructor_impl.data.network.InstructorsApiService
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val instructorMappers: InstructorMappers,
    private val apiService: InstructorsApiService,
    private val networkStateProvider: NetworkStateProvider,
) : InstructorRepository {

    override suspend fun getInstructorsBySportId(sportId: Int): List<Instructor> {
        return makeSafeApiCall(networkStateProvider) {
            apiService.getInstructorsBySportId(sportId + ID_OFFSET)
        }.map(instructorMappers::mapUserRemoteToInstructor)
    }

    companion object {
        const val ID_OFFSET = 1
    }
}
