package com.solopov.feature_user_profile_impl.presentation.user_profile.model

import java.io.Serializable

data class UserProfile(
    var id: String,
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
    var isInstructor: Boolean,
) : Serializable
