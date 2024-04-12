package com.solopov.feature_user_profile_impl.di

import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository
import com.solopov.feature_user_profile_impl.data.repository.UserProfileRepositoryImpl
import com.solopov.common.di.scope.FeatureScope
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
    fun provideUserProfileInteractor(repository: UserProfileRepository): UserProfileInteractor = UserProfileInteractor(repository, Dispatchers.IO)
}
