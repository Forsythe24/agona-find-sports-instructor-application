package com.solopov.common.model

import java.io.Serializable

data class UserCommon (
    var id: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sport: String?,
    var photo: String?,
    var experience: String?,
    var description: String?,
    var rating: Float?,
    var hourlyRate: Float?,
    var isInstructor: Boolean = false
): Serializable