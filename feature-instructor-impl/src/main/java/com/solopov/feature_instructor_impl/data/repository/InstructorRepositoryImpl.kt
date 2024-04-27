package com.solopov.feature_instructor_impl.data.repository

import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import com.solopov.feature_instructor_impl.data.network.InstructorApi
import com.solopov.instructors.R
import java.lang.RuntimeException
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val api: InstructorApi,
    private val db: AppDatabase,
    private val instructorMappers: InstructorMappers,
    private val userFirebaseDao: UserFirebaseDao,
    private val resManager: ResourceManager,
) : InstructorRepository {

    override suspend fun getInstructorsBySportId(sportId: Int): List<Instructor> {
        val offset = if (sportId == 0) 1 else 2
        val domainInstructorsList: MutableList<Instructor> = api.getInstructorsBySportId(sportId + offset)?.instructorsList
            ?.map(instructorMappers::mapInstructorDataToInstructor) as MutableList<Instructor>

        val firebaseInstructorsList = userFirebaseDao.getInstructorsBySport(resManager.getStringArray(R.array.sports_types)[sportId + offset - 1])
            .map(instructorMappers::mapUserFirebaseToInstructor)

        domainInstructorsList.addAll(0, firebaseInstructorsList)
        return domainInstructorsList

    }
}
