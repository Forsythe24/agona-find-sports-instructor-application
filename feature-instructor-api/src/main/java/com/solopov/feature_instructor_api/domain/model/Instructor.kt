package com.solopov.feature_instructor_api.domain.model

data class Instructor(
    val id: String,
    var name: String? = "",
    var age: Int? = 1,
    var gender: String? = "M",
    var sport: String,
    var photo: String? = "",
    var experience: String? = "",
    var description: String? = "",
    var rating: Float? = 0f,
    var hourlyRate: Float? = 0f,
//    var isInstructor: Boolean = true
)


