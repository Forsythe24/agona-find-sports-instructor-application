package com.solopov.feature_instructor_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class InstructorFeatureModule {

    @Provides
    @FeatureScope
    fun provideInstructorRepository(instructorRepository: InstructorRepositoryImpl): InstructorRepository = instructorRepository

    @Provides
    @FeatureScope
    fun provideInstructorInteractor(repository: InstructorRepository): InstructorInteractor {
        return InstructorInteractor(repository, Dispatchers.IO)
    }
}
