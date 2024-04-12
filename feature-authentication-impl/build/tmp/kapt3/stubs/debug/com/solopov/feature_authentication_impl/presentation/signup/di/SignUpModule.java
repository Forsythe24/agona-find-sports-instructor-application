package com.solopov.feature_authentication_impl.presentation.signup.di;

@dagger.Module(includes = {com.solopov.common.di.viewmodel.ViewModelModule.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"Lcom/solopov/feature_authentication_impl/presentation/signup/di/SignUpModule;", "", "()V", "provideMainViewModel", "Lcom/solopov/feature_authentication_impl/presentation/signup/SignUpViewModel;", "fragment", "Landroidx/fragment/app/Fragment;", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "provideSignInViewModel", "Landroidx/lifecycle/ViewModel;", "interactor", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthInteractor;", "exceptionHandlerDelegate", "Lcom/solopov/common/utils/ExceptionHandlerDelegate;", "feature-authentication-impl_debug"})
public final class SignUpModule {
    
    public SignUpModule() {
        super();
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel provideMainViewModel(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.ViewModelProvider.Factory factory) {
        return null;
    }
    
    @dagger.Provides
    @dagger.multibindings.IntoMap
    @com.solopov.common.di.viewmodel.ViewModelKey(value = com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel.class)
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.ViewModel provideSignInViewModel(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor interactor, @org.jetbrains.annotations.NotNull
    com.solopov.common.utils.ExceptionHandlerDelegate exceptionHandlerDelegate) {
        return null;
    }
}