package com.solopov.feature_instructor_impl.data.repository

import com.solopov.common.data.network.dao.UserRemoteDao
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val instructorMappers: InstructorMappers,
    private val userRemoteDao: UserRemoteDao,
) : InstructorRepository {

    override suspend fun getInstructorsBySportId(sportId: Int): List<Instructor> {
        return userRemoteDao.getInstructorsBySportId(sportId + 1)
            .map(instructorMappers::mapUserRemoteToInstructor)

    }
}
