// Generated by Dagger (https://dagger.dev).
package com.solopov.feature_authentication_impl.presentation.signup;

import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository;
import com.solopov.feature_authentication_impl.presentation.SignUpViewModel;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SignUpFragment_MembersInjector implements MembersInjector<SignUpFragment> {
  private final Provider<SignUpViewModel> viewModelProvider;

  private final Provider<AuthRepository> repositoryProvider;

  public SignUpFragment_MembersInjector(Provider<SignUpViewModel> viewModelProvider,
      Provider<AuthRepository> repositoryProvider) {
    this.viewModelProvider = viewModelProvider;
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<SignUpFragment> create(Provider<SignUpViewModel> viewModelProvider,
      Provider<AuthRepository> repositoryProvider) {
    return new SignUpFragment_MembersInjector(viewModelProvider, repositoryProvider);
  }

  @Override
  public void injectMembers(SignUpFragment instance) {
    BaseFragment_MembersInjector.injectViewModel(instance, viewModelProvider.get());
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.solopov.feature_authentication_impl.presentation.signup.SignUpFragment.repository")
  public static void injectRepository(SignUpFragment instance, AuthRepository repository) {
    instance.repository = repository;
  }
}
