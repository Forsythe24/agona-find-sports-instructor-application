package com.solopov.feature_user_profile_impl.di

import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository
import com.solopov.feature_user_profile_impl.data.repository.UserProfileRepositoryImpl
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_user_profile_api.domain.interfaces.RatingRepository
import com.solopov.feature_user_profile_impl.data.repository.RatingRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class UserProfileFeatureModule {

    @Provides
    @FeatureScope
    fun provideUserProfileRepository(repository: UserProfileRepositoryImpl): UserProfileRepository = repository

    @Provides
    @FeatureScope
    fun provideRatingRepository(repository: RatingRepositoryImpl): RatingRepository = repository
    @Provides
    @FeatureScope
    fun provideUserProfileInteractor(userProfileRepository: UserProfileRepository, ratingRepository: RatingRepository): UserProfileInteractor = UserProfileInteractor(userProfileRepository, ratingRepository, Dispatchers.IO)
}
