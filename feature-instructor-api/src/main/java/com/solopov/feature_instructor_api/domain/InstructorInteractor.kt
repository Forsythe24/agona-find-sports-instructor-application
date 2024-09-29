package com.solopov.feature_instructor_api.domain

import com.solopov.feature_instructor_api.domain.model.Instructor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class InstructorInteractor(
    private val instructorRepository: InstructorRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getInstructorsBySportId(sportId: Int): List<Instructor> {
        return withContext(ioDispatcher) {
            instructorRepository.getInstructorsBySportId(sportId)
        }
    }
}
