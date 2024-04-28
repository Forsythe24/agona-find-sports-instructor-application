package com.solopov.feature_chat_api.domain.model

data class User (
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
    var hourlyRate: Float?,
    var isInstructor: Boolean = false
)
