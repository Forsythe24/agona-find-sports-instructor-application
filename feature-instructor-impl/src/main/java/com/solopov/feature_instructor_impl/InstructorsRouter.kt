package com.solopov.feature_instructor_impl

import com.solopov.common.model.UserCommon

interface InstructorsRouter {
    fun openInstructor(instructorId: String)
    fun openInstructor(instructor: UserCommon)

    fun returnToInstructors()
}
