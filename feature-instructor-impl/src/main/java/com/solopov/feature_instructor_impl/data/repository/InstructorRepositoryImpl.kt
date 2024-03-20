package com.solopov.feature_instructor_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import com.solopov.feature_instructor_impl.data.network.InstructorApi
import java.lang.RuntimeException
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val api: InstructorApi,
    private val db: AppDatabase,
    private val instructorMappers: InstructorMappers
) : InstructorRepository {

    override suspend fun getInstructorsBySportId(id: Int): List<Instructor> {
        val domainInstructorsList = api.getInstructors(id)?.instructorsList?.map(instructorMappers::mapInstructorDataToInstructor)
        return domainInstructorsList ?: throw RuntimeException("No instructors specializing in this sport")

    }
}
