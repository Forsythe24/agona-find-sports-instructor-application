package com.solopov.feature_instructor_impl.presentation.details.model

data class InstructorDetailsModel(
    val id: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sport: String,
    var photo: String,
    var experience: String,
    var description: String,
    var rating: Float,
    var hourlyRate: Float,
)
