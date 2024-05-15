package com.solopov.common.data.remote.model

data class UserSignUpRemote (
    var id: String,
    var email: String,
    var password: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sportName: String?,
    var photo: String?,
    var experience: String?,
    var description: String?,
    var rating: Float?,
    var numberOfRatings: Int?,
    var hourlyRate: Float?,
    var isInstructor: Boolean = false
)
