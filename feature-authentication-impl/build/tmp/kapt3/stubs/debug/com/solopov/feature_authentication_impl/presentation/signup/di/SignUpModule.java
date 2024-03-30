package com.solopov.feature_authentication_impl.presentation.signup.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/solopov/feature_authentication_impl/presentation/signup/di/SignUpModule;", "", "()V", "provideMainViewModel", "Lcom/solopov/feature_authentication_impl/presentation/SignUpViewModel;", "fragment", "Landroidx/fragment/app/Fragment;", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "provideSignInViewModel", "Landroidx/lifecycle/ViewModel;", "feature-authentication-impl_debug"})
@dagger.Module(includes = {com.solopov.common.di.viewmodel.ViewModelModule.class})
public final class SignUpModule {
    
    public SignUpModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @dagger.Provides
    public final com.solopov.feature_authentication_impl.presentation.SignUpViewModel provideMainViewModel(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.ViewModelProvider.Factory factory) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @com.solopov.common.di.viewmodel.ViewModelKey(value = com.solopov.feature_authentication_impl.presentation.SignUpViewModel.class)
    @dagger.multibindings.IntoMap
    @dagger.Provides
    public final androidx.lifecycle.ViewModel provideSignInViewModel() {
        return null;
    }
}