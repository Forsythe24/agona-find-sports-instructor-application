package com.solopov.feature_instructor_api.domain.usecase

import com.solopov.feature_instructor_api.domain.model.Instructor

interface LoadSportInstructorsUseCase {
    suspend operator fun invoke(sportId: Int): List<Instructor>
}
