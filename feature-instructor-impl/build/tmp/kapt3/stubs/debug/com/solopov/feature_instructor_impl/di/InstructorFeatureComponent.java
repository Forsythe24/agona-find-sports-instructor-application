package com.solopov.feature_instructor_impl.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0002\u0006\u0007J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\b"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureComponent;", "Lcom/solopov/feature_instructor_api/di/InstructorFeatureApi;", "instructorComponentFactory", "Lcom/solopov/feature_instructor_impl/presentation/details/di/InstructorComponent$Factory;", "instructorsComponentFactory", "Lcom/solopov/feature_instructor_impl/presentation/list/di/InstructorsComponent$Factory;", "Builder", "InstructorFeatureDependenciesComponent", "feature-instructor-impl_debug"})
@dagger.Component(dependencies = {com.solopov.feature_instructor_impl.di.InstructorFeatureDependencies.class}, modules = {com.solopov.feature_instructor_impl.di.InstructorFeatureModule.class})
@com.solopov.common.di.scope.FeatureScope
public abstract interface InstructorFeatureComponent extends com.solopov.feature_instructor_api.di.InstructorFeatureApi {
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.feature_instructor_impl.presentation.list.di.InstructorsComponent.Factory instructorsComponentFactory();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.feature_instructor_impl.presentation.details.di.InstructorComponent.Factory instructorComponentFactory();
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureComponent$Builder;", "", "build", "Lcom/solopov/feature_instructor_impl/di/InstructorFeatureComponent;", "router", "instructorsRouter", "Lcom/solopov/feature_instructor_impl/InstructorsRouter;", "withDependencies", "deps", "Lcom/solopov/feature_instructor_impl/di/InstructorFeatureDependencies;", "feature-instructor-impl_debug"})
    @dagger.Component.Builder
    public static abstract interface Builder {
        
        @org.jetbrains.annotations.NotNull
        @dagger.BindsInstance
        public abstract com.solopov.feature_instructor_impl.di.InstructorFeatureComponent.Builder router(@org.jetbrains.annotations.NotNull
        com.solopov.feature_instructor_impl.InstructorsRouter instructorsRouter);
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_instructor_impl.di.InstructorFeatureComponent.Builder withDependencies(@org.jetbrains.annotations.NotNull
        com.solopov.feature_instructor_impl.di.InstructorFeatureDependencies deps);
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_instructor_impl.di.InstructorFeatureComponent build();
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureComponent$InstructorFeatureDependenciesComponent;", "Lcom/solopov/feature_instructor_impl/di/InstructorFeatureDependencies;", "feature-instructor-impl_debug"})
    @dagger.Component(dependencies = {com.solopov.common.di.CommonApi.class, com.solopov.common.data.db.di.DbApi.class})
    public static abstract interface InstructorFeatureDependenciesComponent extends com.solopov.feature_instructor_impl.di.InstructorFeatureDependencies {
    }
}