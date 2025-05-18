package com.solopov.feature_instructor_impl.di

import com.solopov.common.data.network.di.qualifier.AuthenticatedClient
import com.solopov.common.data.network.utils.NetworkApiCreator
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_instructor_api.domain.InstructorRepository
import com.solopov.feature_instructor_impl.data.network.InstructorsApiService
import com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class InstructorFeatureModule {

    @Provides
    @FeatureScope
    fun provideInstructorRepository(instructorRepository: InstructorRepositoryImpl): InstructorRepository = instructorRepository

    @Provides
    @FeatureScope
    fun provideInstructorsApiService(
        @AuthenticatedClient
        okHttpClient: OkHttpClient,
        apiCreator: NetworkApiCreator,
    ): InstructorsApiService {
        apiCreator.okHttpClient = okHttpClient
        return apiCreator.create(InstructorsApiService::class.java)
    }
}
