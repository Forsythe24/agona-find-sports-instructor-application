package com.solopov.feature_instructor_impl.presentation.details.di;

@dagger.Subcomponent(modules = {com.solopov.feature_instructor_impl.presentation.details.di.InstructorModule.class})
@com.solopov.common.di.scope.ScreenScope
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/details/di/InstructorComponent;", "", "inject", "", "fragment", "Lcom/solopov/feature_instructor_impl/presentation/details/InstructorFragment;", "Factory", "feature-instructor-impl_debug"})
public abstract interface InstructorComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.presentation.details.InstructorFragment fragment);
    
    @dagger.Subcomponent.Factory
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/details/di/InstructorComponent$Factory;", "", "create", "Lcom/solopov/feature_instructor_impl/presentation/details/di/InstructorComponent;", "fragment", "Landroidx/fragment/app/Fragment;", "instructorId", "", "feature-instructor-impl_debug"})
    public static abstract interface Factory {
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_instructor_impl.presentation.details.di.InstructorComponent create(@dagger.BindsInstance
        @org.jetbrains.annotations.NotNull
        androidx.fragment.app.Fragment fragment, @dagger.BindsInstance
        @org.jetbrains.annotations.NotNull
        java.lang.String instructorId);
    }
}