package com.solopov.common.data.firebase.model

data class UserFirebase (
    var id: String,
    var email: String,
    var password: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sport: String?,
    var photo: String?,
    var experience: String?,
    var description: String?,
    var rating: Float?,
    var numberOfRatings: Int?,
    var hourlyRate: Float?,
    var isInstructor: Boolean = false
)
