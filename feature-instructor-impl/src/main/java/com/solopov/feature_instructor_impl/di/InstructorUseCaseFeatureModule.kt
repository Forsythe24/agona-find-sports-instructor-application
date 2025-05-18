package com.solopov.feature_instructor_impl.di

import com.solopov.feature_instructor_api.domain.usecase.LoadSportInstructorsUseCase
import com.solopov.feature_instructor_impl.domain.usecase.LoadSportInstructorsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface InstructorUseCaseFeatureModule {

    @Binds
    fun bindLoadSportInstructorsUseCase(impl: LoadSportInstructorsUseCaseImpl): LoadSportInstructorsUseCase
}
