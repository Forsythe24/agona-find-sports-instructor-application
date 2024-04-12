package com.solopov.feature_authentication_impl.presentation.signup;

import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.feature_authentication_impl.AuthRouter;
import com.solopov.feature_authentication_impl.utils.UserDataValidator;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SignUpFragment_MembersInjector implements MembersInjector<SignUpFragment> {
  private final Provider<SignUpViewModel> viewModelProvider;

  private final Provider<AuthRouter> routerProvider;

  private final Provider<UserDataValidator> validatorProvider;

  public SignUpFragment_MembersInjector(Provider<SignUpViewModel> viewModelProvider,
      Provider<AuthRouter> routerProvider, Provider<UserDataValidator> validatorProvider) {
    this.viewModelProvider = viewModelProvider;
    this.routerProvider = routerProvider;
    this.validatorProvider = validatorProvider;
  }

  public static MembersInjector<SignUpFragment> create(Provider<SignUpViewModel> viewModelProvider,
      Provider<AuthRouter> routerProvider, Provider<UserDataValidator> validatorProvider) {
    return new SignUpFragment_MembersInjector(viewModelProvider, routerProvider, validatorProvider);
  }

  @Override
  public void injectMembers(SignUpFragment instance) {
    BaseFragment_MembersInjector.injectViewModel(instance, viewModelProvider.get());
    injectRouter(instance, routerProvider.get());
    injectValidator(instance, validatorProvider.get());
  }

  @InjectedFieldSignature("com.solopov.feature_authentication_impl.presentation.signup.SignUpFragment.router")
  public static void injectRouter(SignUpFragment instance, AuthRouter router) {
    instance.router = router;
  }

  @InjectedFieldSignature("com.solopov.feature_authentication_impl.presentation.signup.SignUpFragment.validator")
  public static void injectValidator(SignUpFragment instance, UserDataValidator validator) {
    instance.validator = validator;
  }
}
