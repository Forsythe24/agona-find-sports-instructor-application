package com.solopov.feature_user_profile_api.di

import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository

interface UserProfileFeatureApi {

    fun provideUserProfileInteractor(): UserProfileInteractor
    fun provideAuthRepository(): UserProfileRepository
}
