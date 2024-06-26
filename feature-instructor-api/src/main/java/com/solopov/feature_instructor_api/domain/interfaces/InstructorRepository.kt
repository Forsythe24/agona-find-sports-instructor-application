package com.solopov.feature_instructor_api.domain.interfaces

import com.solopov.feature_instructor_api.domain.model.Instructor

interface InstructorRepository {
    suspend fun getInstructorsBySportId(sportId: Int): List<Instructor>
}
