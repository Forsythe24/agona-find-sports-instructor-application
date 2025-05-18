package com.solopov.feature_instructor_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_instructor_api.domain.InstructorRepository
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_api.domain.usecase.LoadSportInstructorsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadSportInstructorsUseCaseImpl @Inject constructor(
    private val instructorRepository: InstructorRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): LoadSportInstructorsUseCase {
    override suspend fun invoke(sportId: Int): List<Instructor> {
        return withContext(ioDispatcher) {
            instructorRepository.getInstructorsBySportId(sportId)
        }
    }
}
