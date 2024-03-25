package com.solopov.feature_instructor_impl.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureModule;", "", "()V", "provideInstructorApi", "Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;", "apiCreator", "Lcom/solopov/common/data/network/NetworkApiCreator;", "provideInstructorInteractor", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;", "repository", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorRepository;", "provideInstructorRepository", "instructorRepository", "Lcom/solopov/feature_instructor_impl/data/repository/InstructorRepositoryImpl;", "feature-instructor-impl_debug"})
@dagger.Module
public final class InstructorFeatureModule {
    
    public InstructorFeatureModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @com.solopov.common.di.scope.FeatureScope
    @dagger.Provides
    public final com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository provideInstructorRepository(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl instructorRepository) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @com.solopov.common.di.scope.FeatureScope
    @dagger.Provides
    public final com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor provideInstructorInteractor(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository repository) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @com.solopov.common.di.scope.FeatureScope
    @dagger.Provides
    public final com.solopov.feature_instructor_impl.data.network.InstructorApi provideInstructorApi(@org.jetbrains.annotations.NotNull
    com.solopov.common.data.network.NetworkApiCreator apiCreator) {
        return null;
    }
}