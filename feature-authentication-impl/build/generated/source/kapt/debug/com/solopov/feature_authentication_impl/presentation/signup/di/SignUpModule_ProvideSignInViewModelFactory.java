package com.solopov.feature_authentication_impl.presentation.signup.di;

import androidx.lifecycle.ViewModel;
import com.solopov.common.utils.ExceptionHandlerDelegate;
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SignUpModule_ProvideSignInViewModelFactory implements Factory<ViewModel> {
  private final SignUpModule module;

  private final Provider<AuthInteractor> interactorProvider;

  private final Provider<ExceptionHandlerDelegate> exceptionHandlerDelegateProvider;

  public SignUpModule_ProvideSignInViewModelFactory(SignUpModule module,
      Provider<AuthInteractor> interactorProvider,
      Provider<ExceptionHandlerDelegate> exceptionHandlerDelegateProvider) {
    this.module = module;
    this.interactorProvider = interactorProvider;
    this.exceptionHandlerDelegateProvider = exceptionHandlerDelegateProvider;
  }

  @Override
  public ViewModel get() {
    return provideSignInViewModel(module, interactorProvider.get(), exceptionHandlerDelegateProvider.get());
  }

  public static SignUpModule_ProvideSignInViewModelFactory create(SignUpModule module,
      Provider<AuthInteractor> interactorProvider,
      Provider<ExceptionHandlerDelegate> exceptionHandlerDelegateProvider) {
    return new SignUpModule_ProvideSignInViewModelFactory(module, interactorProvider, exceptionHandlerDelegateProvider);
  }

  public static ViewModel provideSignInViewModel(SignUpModule instance, AuthInteractor interactor,
      ExceptionHandlerDelegate exceptionHandlerDelegate) {
    return Preconditions.checkNotNullFromProvides(instance.provideSignInViewModel(interactor, exceptionHandlerDelegate));
  }
}
