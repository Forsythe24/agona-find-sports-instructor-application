// Generated by Dagger (https://dagger.dev).
package com.solopov.feature_authentication_impl.data.di;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.common.data.firebase.dao.UserFirebaseDao;
import com.solopov.common.di.viewmodel.ViewModelProviderFactory;
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor;
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository;
import com.solopov.feature_authentication_impl.data.mappers.UserMappers_Factory;
import com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl;
import com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl_Factory;
import com.solopov.feature_authentication_impl.presentation.SignUpViewModel;
import com.solopov.feature_authentication_impl.presentation.signup.SignUpFragment;
import com.solopov.feature_authentication_impl.presentation.signup.SignUpFragment_MembersInjector;
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpComponent;
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpModule;
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpModule_ProvideMainViewModelFactory;
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpModule_ProvideSignInViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Collections;
import java.util.Map;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerAuthFeatureComponent {
  private DaggerAuthFeatureComponent() {
  }

  public static AuthFeatureComponent.Builder builder() {
    return new Builder();
  }

  private static final class Builder implements AuthFeatureComponent.Builder {
    private AuthFeatureDependencies authFeatureDependencies;

    @Override
    public Builder withDependencies(AuthFeatureDependencies deps) {
      this.authFeatureDependencies = Preconditions.checkNotNull(deps);
      return this;
    }

    @Override
    public AuthFeatureComponent build() {
      Preconditions.checkBuilderRequirement(authFeatureDependencies, AuthFeatureDependencies.class);
      return new AuthFeatureComponentImpl(new AuthFeatureModule(), authFeatureDependencies);
    }
  }

  private static final class SignUpComponentFactory implements SignUpComponent.Factory {
    private final AuthFeatureComponentImpl authFeatureComponentImpl;

    private SignUpComponentFactory(AuthFeatureComponentImpl authFeatureComponentImpl) {
      this.authFeatureComponentImpl = authFeatureComponentImpl;
    }

    @Override
    public SignUpComponent create(Fragment fragment) {
      Preconditions.checkNotNull(fragment);
      return new SignUpComponentImpl(authFeatureComponentImpl, new SignUpModule(), fragment);
    }
  }

  private static final class SignUpComponentImpl implements SignUpComponent {
    private final SignUpModule signUpModule;

    private final Fragment fragment;

    private final AuthFeatureComponentImpl authFeatureComponentImpl;

    private final SignUpComponentImpl signUpComponentImpl = this;

    private Provider<ViewModel> provideSignInViewModelProvider;

    private SignUpComponentImpl(AuthFeatureComponentImpl authFeatureComponentImpl,
        SignUpModule signUpModuleParam, Fragment fragmentParam) {
      this.authFeatureComponentImpl = authFeatureComponentImpl;
      this.signUpModule = signUpModuleParam;
      this.fragment = fragmentParam;
      initialize(signUpModuleParam, fragmentParam);

    }

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> mapOfClassOfAndProviderOfViewModel(
        ) {
      return Collections.<Class<? extends ViewModel>, Provider<ViewModel>>singletonMap(SignUpViewModel.class, provideSignInViewModelProvider);
    }

    private ViewModelProviderFactory viewModelProviderFactory() {
      return new ViewModelProviderFactory(mapOfClassOfAndProviderOfViewModel());
    }

    private SignUpViewModel signUpViewModel() {
      return SignUpModule_ProvideMainViewModelFactory.provideMainViewModel(signUpModule, fragment, viewModelProviderFactory());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SignUpModule signUpModuleParam, final Fragment fragmentParam) {
      this.provideSignInViewModelProvider = SignUpModule_ProvideSignInViewModelFactory.create(signUpModuleParam);
    }

    @Override
    public void inject(SignUpFragment fragment) {
      injectSignUpFragment(fragment);
    }

    private SignUpFragment injectSignUpFragment(SignUpFragment instance) {
      BaseFragment_MembersInjector.injectViewModel(instance, signUpViewModel());
      SignUpFragment_MembersInjector.injectRepository(instance, authFeatureComponentImpl.provideAuthRepositoryProvider.get());
      return instance;
    }
  }

  private static final class AuthFeatureComponentImpl implements AuthFeatureComponent {
    private final AuthFeatureComponentImpl authFeatureComponentImpl = this;

    private Provider<UserFirebaseDao> userFirebaseDaoProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<AuthRepository> provideAuthRepositoryProvider;

    private Provider<AuthInteractor> provideAuthInteractorProvider;

    private AuthFeatureComponentImpl(AuthFeatureModule authFeatureModuleParam,
        AuthFeatureDependencies authFeatureDependenciesParam) {

      initialize(authFeatureModuleParam, authFeatureDependenciesParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final AuthFeatureModule authFeatureModuleParam,
        final AuthFeatureDependencies authFeatureDependenciesParam) {
      this.userFirebaseDaoProvider = new UserFirebaseDaoProvider(authFeatureDependenciesParam);
      this.authRepositoryImplProvider = AuthRepositoryImpl_Factory.create(userFirebaseDaoProvider, UserMappers_Factory.create());
      this.provideAuthRepositoryProvider = DoubleCheck.provider(AuthFeatureModule_ProvideAuthRepositoryFactory.create(authFeatureModuleParam, authRepositoryImplProvider));
      this.provideAuthInteractorProvider = DoubleCheck.provider(AuthFeatureModule_ProvideAuthInteractorFactory.create(authFeatureModuleParam, provideAuthRepositoryProvider));
    }

    @Override
    public AuthInteractor provideAuthInteractor() {
      return provideAuthInteractorProvider.get();
    }

    @Override
    public AuthRepository provideAuthRepository() {
      return provideAuthRepositoryProvider.get();
    }

    @Override
    public SignUpComponent.Factory signUpComponentFactory() {
      return new SignUpComponentFactory(authFeatureComponentImpl);
    }

    private static final class UserFirebaseDaoProvider implements Provider<UserFirebaseDao> {
      private final AuthFeatureDependencies authFeatureDependencies;

      UserFirebaseDaoProvider(AuthFeatureDependencies authFeatureDependencies) {
        this.authFeatureDependencies = authFeatureDependencies;
      }

      @Override
      public UserFirebaseDao get() {
        return Preconditions.checkNotNullFromComponent(authFeatureDependencies.userFirebaseDao());
      }
    }
  }
}
