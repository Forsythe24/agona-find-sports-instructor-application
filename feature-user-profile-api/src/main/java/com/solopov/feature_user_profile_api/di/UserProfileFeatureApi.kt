package com.solopov.feature_user_profile_api.di

import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.usecase.GetCurrentUserUseCase

interface UserProfileFeatureApi {
    fun provideGetCurrentUserUseCase(): GetCurrentUserUseCase
    fun provideUserProfileRepository(): UserProfileRepository
    fun provideRatingRepository(): RatingRepository
}
