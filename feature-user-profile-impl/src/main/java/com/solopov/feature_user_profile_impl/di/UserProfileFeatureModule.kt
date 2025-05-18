package com.solopov.feature_user_profile_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_impl.data.repository.RatingRepositoryImpl
import com.solopov.feature_user_profile_impl.data.repository.UserProfileRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface UserProfileFeatureModule {

    @Binds
    @FeatureScope
    fun provideUserProfileRepository(repository: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @FeatureScope
    fun provideRatingRepository(repository: RatingRepositoryImpl): RatingRepository
}
