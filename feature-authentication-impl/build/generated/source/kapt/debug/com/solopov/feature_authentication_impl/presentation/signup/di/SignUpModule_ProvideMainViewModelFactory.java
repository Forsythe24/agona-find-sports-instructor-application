package com.solopov.feature_authentication_impl.presentation.signup.di;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel;
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
public final class SignUpModule_ProvideMainViewModelFactory implements Factory<SignUpViewModel> {
  private final SignUpModule module;

  private final Provider<Fragment> fragmentProvider;

  private final Provider<ViewModelProvider.Factory> factoryProvider;

  public SignUpModule_ProvideMainViewModelFactory(SignUpModule module,
      Provider<Fragment> fragmentProvider, Provider<ViewModelProvider.Factory> factoryProvider) {
    this.module = module;
    this.fragmentProvider = fragmentProvider;
    this.factoryProvider = factoryProvider;
  }

  @Override
  public SignUpViewModel get() {
    return provideMainViewModel(module, fragmentProvider.get(), factoryProvider.get());
  }

  public static SignUpModule_ProvideMainViewModelFactory create(SignUpModule module,
      Provider<Fragment> fragmentProvider, Provider<ViewModelProvider.Factory> factoryProvider) {
    return new SignUpModule_ProvideMainViewModelFactory(module, fragmentProvider, factoryProvider);
  }

  public static SignUpViewModel provideMainViewModel(SignUpModule instance, Fragment fragment,
      ViewModelProvider.Factory factory) {
    return Preconditions.checkNotNullFromProvides(instance.provideMainViewModel(fragment, factory));
  }
}
