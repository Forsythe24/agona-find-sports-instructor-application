package com.solopov.feature_instructor_impl.di

import com.solopov.common.data.network.NetworkApiCreator
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository
import com.solopov.feature_instructor_impl.data.network.InstructorApi
import com.solopov.feature_instructor_impl.data.network.interceptors.ApiInfoInterceptor
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
    fun provideInstructorInteractor(repository: InstructorRepository): InstructorInteractor {
        return InstructorInteractor(repository)
    }

    @Provides
    @FeatureScope
    fun provideOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient.newBuilder().addInterceptor(ApiInfoInterceptor()).build()
    }

    @Provides
    @FeatureScope
    fun provideInstructorApi(apiCreator: NetworkApiCreator): InstructorApi {
        return apiCreator.create(InstructorApi::class.java)
    }
}
