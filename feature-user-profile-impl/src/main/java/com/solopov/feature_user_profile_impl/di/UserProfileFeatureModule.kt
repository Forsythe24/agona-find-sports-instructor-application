package com.solopov.feature_user_profile_impl.di

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.UserProfileInteractor
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_impl.data.repository.RatingRepositoryImpl
import com.solopov.feature_user_profile_impl.data.repository.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

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
    fun provideUserProfileInteractor(
        userProfileRepository: UserProfileRepository,
        ratingRepository: RatingRepository,
        @IoDispatcher
        ioDispatcher: CoroutineDispatcher,
    ): UserProfileInteractor =
        UserProfileInteractor(userProfileRepository, ratingRepository, ioDispatcher)
}
