package com.solopov.feature_instructor_impl.presentation.details.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/details/di/InstructorModule;", "", "()V", "provideInstructorViewModel", "Landroidx/lifecycle/ViewModel;", "interactor", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;", "instructorId", "", "resourceManager", "Lcom/solopov/common/core/resources/ResourceManager;", "provideMainViewModel", "Lcom/solopov/feature_instructor_impl/presentation/details/InstructorViewModel;", "fragment", "Landroidx/fragment/app/Fragment;", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "feature-instructor-impl_debug"})
@dagger.Module(includes = {com.solopov.common.di.viewmodel.ViewModelModule.class})
public final class InstructorModule {
    
    public InstructorModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @dagger.Provides
    public final com.solopov.feature_instructor_impl.presentation.details.InstructorViewModel provideMainViewModel(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.ViewModelProvider.Factory factory) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @com.solopov.common.di.viewmodel.ViewModelKey(value = com.solopov.feature_instructor_impl.presentation.details.InstructorViewModel.class)
    @dagger.multibindings.IntoMap
    @dagger.Provides
    public final androidx.lifecycle.ViewModel provideInstructorViewModel(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor interactor, @org.jetbrains.annotations.NotNull
    java.lang.String instructorId, @org.jetbrains.annotations.NotNull
    com.solopov.common.core.resources.ResourceManager resourceManager) {
        return null;
    }
}