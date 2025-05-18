package com.solopov.feature_user_profile_api.di

import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.UserProfileRepository

interface UserProfileFeatureApi {
    fun provideUserProfileRepository(): UserProfileRepository
    fun provideRatingRepository(): RatingRepository
}
