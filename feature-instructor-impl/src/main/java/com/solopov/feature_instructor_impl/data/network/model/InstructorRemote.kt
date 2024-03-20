package com.solopov.feature_instructor_impl.data.network.model

data class InstructorRemote(
    val id: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sport: String,
    var photo: String?,
    var rating: Float? = 0f,
)
