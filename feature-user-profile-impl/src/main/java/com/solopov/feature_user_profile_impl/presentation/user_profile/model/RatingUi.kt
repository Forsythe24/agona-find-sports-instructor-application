package com.solopov.feature_user_profile_impl.presentation.user_profile.model

data class RatingUi(
    var id: Int?,
    val instructorId: String,
    val userId: String,
    val rating: Float,
)
