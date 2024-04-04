package com.solopov.feature_instructor_impl.di;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.common.core.resources.ResourceManager;
import com.solopov.common.data.db.AppDatabase;
import com.solopov.common.data.network.NetworkApiCreator;
import com.solopov.common.di.viewmodel.ViewModelProviderFactory;
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor;
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository;
import com.solopov.feature_instructor_impl.InstructorsRouter;
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers_Factory;
import com.solopov.feature_instructor_impl.data.network.InstructorApi;
import com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl;
import com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl_Factory;
import com.solopov.feature_instructor_impl.presentation.details.InstructorFragment;
import com.solopov.feature_instructor_impl.presentation.details.InstructorFragment_MembersInjector;
import com.solopov.feature_instructor_impl.presentation.details.InstructorViewModel;
import com.solopov.feature_instructor_impl.presentation.details.di.InstructorComponent;
import com.solopov.feature_instructor_impl.presentation.details.di.InstructorModule;
import com.solopov.feature_instructor_impl.presentation.details.di.InstructorModule_ProvideInstructorViewModelFactory;
import com.solopov.feature_instructor_impl.presentation.details.di.InstructorModule_ProvideMainViewModelFactory;
import com.solopov.feature_instructor_impl.presentation.list.InstructorsViewModel;
import com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment;
import com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment_MembersInjector;
import com.solopov.feature_instructor_impl.presentation.list.di.InstructorsComponent;
import com.solopov.feature_instructor_impl.presentation.list.di.InstructorsModule;
import com.solopov.feature_instructor_impl.presentation.list.di.InstructorsModule_ProvideInstructorsViewModelFactory;
import com.solopov.feature_instructor_impl.presentation.list.di.InstructorsModule_ProvideMainViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import java.util.Collections;
import java.util.Map;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerInstructorFeatureComponent {
  private DaggerInstructorFeatureComponent() {
  }

  public static InstructorFeatureComponent.Builder builder() {
    return new Builder();
  }

  private static final class Builder implements InstructorFeatureComponent.Builder {
    private InstructorsRouter router;

    private InstructorFeatureDependencies instructorFeatureDependencies;

    @Override
    public Builder router(InstructorsRouter instructorsRouter) {
      this.router = Preconditions.checkNotNull(instructorsRouter);
      return this;
    }

    @Override
    public Builder withDependencies(InstructorFeatureDependencies deps) {
      this.instructorFeatureDependencies = Preconditions.checkNotNull(deps);
      return this;
    }

    @Override
    public InstructorFeatureComponent build() {
      Preconditions.checkBuilderRequirement(router, InstructorsRouter.class);
      Preconditions.checkBuilderRequirement(instructorFeatureDependencies, InstructorFeatureDependencies.class);
      return new InstructorFeatureComponentImpl(new InstructorFeatureModule(), instructorFeatureDependencies, router);
    }
  }

  private static final class InstructorsComponentFactory implements InstructorsComponent.Factory {
    private final InstructorFeatureComponentImpl instructorFeatureComponentImpl;

    private InstructorsComponentFactory(
        InstructorFeatureComponentImpl instructorFeatureComponentImpl) {
      this.instructorFeatureComponentImpl = instructorFeatureComponentImpl;
    }

    @Override
    public InstructorsComponent create(Fragment fragment) {
      Preconditions.checkNotNull(fragment);
      return new InstructorsComponentImpl(instructorFeatureComponentImpl, new InstructorsModule(), fragment);
    }
  }

  private static final class InstructorComponentFactory implements InstructorComponent.Factory {
    private final InstructorFeatureComponentImpl instructorFeatureComponentImpl;

    private InstructorComponentFactory(
        InstructorFeatureComponentImpl instructorFeatureComponentImpl) {
      this.instructorFeatureComponentImpl = instructorFeatureComponentImpl;
    }

    @Override
    public InstructorComponent create(Fragment fragment, String instructorId) {
      Preconditions.checkNotNull(fragment);
      Preconditions.checkNotNull(instructorId);
      return new InstructorComponentImpl(instructorFeatureComponentImpl, new InstructorModule(), fragment, instructorId);
    }
  }

  private static final class InstructorsComponentImpl implements InstructorsComponent {
    private final InstructorsModule instructorsModule;

    private final Fragment fragment;

    private final InstructorFeatureComponentImpl instructorFeatureComponentImpl;

    private final InstructorsComponentImpl instructorsComponentImpl = this;

    private Provider<ViewModel> provideInstructorsViewModelProvider;

    private InstructorsComponentImpl(InstructorFeatureComponentImpl instructorFeatureComponentImpl,
        InstructorsModule instructorsModuleParam, Fragment fragmentParam) {
      this.instructorFeatureComponentImpl = instructorFeatureComponentImpl;
      this.instructorsModule = instructorsModuleParam;
      this.fragment = fragmentParam;
      initialize(instructorsModuleParam, fragmentParam);

    }

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> mapOfClassOfAndProviderOfViewModel(
        ) {
      return Collections.<Class<? extends ViewModel>, Provider<ViewModel>>singletonMap(InstructorsViewModel.class, provideInstructorsViewModelProvider);
    }

    private ViewModelProviderFactory viewModelProviderFactory() {
      return new ViewModelProviderFactory(mapOfClassOfAndProviderOfViewModel());
    }

    private InstructorsViewModel instructorsViewModel() {
      return InstructorsModule_ProvideMainViewModelFactory.provideMainViewModel(instructorsModule, fragment, viewModelProviderFactory());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final InstructorsModule instructorsModuleParam,
        final Fragment fragmentParam) {
      this.provideInstructorsViewModelProvider = InstructorsModule_ProvideInstructorsViewModelFactory.create(instructorsModuleParam, instructorFeatureComponentImpl.provideInstructorInteractorProvider, instructorFeatureComponentImpl.resourceManagerProvider);
    }

    @Override
    public void inject(OneSportInstructorsFragment fragment) {
      injectOneSportInstructorsFragment(fragment);
    }

    private OneSportInstructorsFragment injectOneSportInstructorsFragment(
        OneSportInstructorsFragment instance) {
      BaseFragment_MembersInjector.injectViewModel(instance, instructorsViewModel());
      OneSportInstructorsFragment_MembersInjector.injectRouter(instance, instructorFeatureComponentImpl.router);
      return instance;
    }
  }

  private static final class InstructorComponentImpl implements InstructorComponent {
    private final InstructorModule instructorModule;

    private final Fragment fragment;

    private final InstructorFeatureComponentImpl instructorFeatureComponentImpl;

    private final InstructorComponentImpl instructorComponentImpl = this;

    private Provider<String> instructorIdProvider;

    private Provider<ViewModel> provideInstructorViewModelProvider;

    private InstructorComponentImpl(InstructorFeatureComponentImpl instructorFeatureComponentImpl,
        InstructorModule instructorModuleParam, Fragment fragmentParam, String instructorIdParam) {
      this.instructorFeatureComponentImpl = instructorFeatureComponentImpl;
      this.instructorModule = instructorModuleParam;
      this.fragment = fragmentParam;
      initialize(instructorModuleParam, fragmentParam, instructorIdParam);

    }

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> mapOfClassOfAndProviderOfViewModel(
        ) {
      return Collections.<Class<? extends ViewModel>, Provider<ViewModel>>singletonMap(InstructorViewModel.class, provideInstructorViewModelProvider);
    }

    private ViewModelProviderFactory viewModelProviderFactory() {
      return new ViewModelProviderFactory(mapOfClassOfAndProviderOfViewModel());
    }

    private InstructorViewModel instructorViewModel() {
      return InstructorModule_ProvideMainViewModelFactory.provideMainViewModel(instructorModule, fragment, viewModelProviderFactory());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final InstructorModule instructorModuleParam,
        final Fragment fragmentParam, final String instructorIdParam) {
      this.instructorIdProvider = InstanceFactory.create(instructorIdParam);
      this.provideInstructorViewModelProvider = InstructorModule_ProvideInstructorViewModelFactory.create(instructorModuleParam, instructorFeatureComponentImpl.provideInstructorInteractorProvider, instructorIdProvider, instructorFeatureComponentImpl.resourceManagerProvider);
    }

    @Override
    public void inject(InstructorFragment fragment) {
      injectInstructorFragment(fragment);
    }

    private InstructorFragment injectInstructorFragment(InstructorFragment instance) {
      BaseFragment_MembersInjector.injectViewModel(instance, instructorViewModel());
      InstructorFragment_MembersInjector.injectRouter(instance, instructorFeatureComponentImpl.router);
      return instance;
    }
  }

  private static final class InstructorFeatureComponentImpl implements InstructorFeatureComponent {
    private final InstructorsRouter router;

    private final InstructorFeatureComponentImpl instructorFeatureComponentImpl = this;

    private Provider<NetworkApiCreator> networkApiCreatorProvider;

    private Provider<InstructorApi> provideInstructorApiProvider;

    private Provider<AppDatabase> dbProvider;

    private Provider<InstructorRepositoryImpl> instructorRepositoryImplProvider;

    private Provider<InstructorRepository> provideInstructorRepositoryProvider;

    private Provider<InstructorInteractor> provideInstructorInteractorProvider;

    private Provider<ResourceManager> resourceManagerProvider;

    private InstructorFeatureComponentImpl(InstructorFeatureModule instructorFeatureModuleParam,
        InstructorFeatureDependencies instructorFeatureDependenciesParam,
        InstructorsRouter routerParam) {
      this.router = routerParam;
      initialize(instructorFeatureModuleParam, instructorFeatureDependenciesParam, routerParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final InstructorFeatureModule instructorFeatureModuleParam,
        final InstructorFeatureDependencies instructorFeatureDependenciesParam,
        final InstructorsRouter routerParam) {
      this.networkApiCreatorProvider = new NetworkApiCreatorProvider(instructorFeatureDependenciesParam);
      this.provideInstructorApiProvider = DoubleCheck.provider(InstructorFeatureModule_ProvideInstructorApiFactory.create(instructorFeatureModuleParam, networkApiCreatorProvider));
      this.dbProvider = new DbProvider(instructorFeatureDependenciesParam);
      this.instructorRepositoryImplProvider = InstructorRepositoryImpl_Factory.create(provideInstructorApiProvider, dbProvider, InstructorMappers_Factory.create());
      this.provideInstructorRepositoryProvider = DoubleCheck.provider(InstructorFeatureModule_ProvideInstructorRepositoryFactory.create(instructorFeatureModuleParam, instructorRepositoryImplProvider));
      this.provideInstructorInteractorProvider = DoubleCheck.provider(InstructorFeatureModule_ProvideInstructorInteractorFactory.create(instructorFeatureModuleParam, provideInstructorRepositoryProvider));
      this.resourceManagerProvider = new ResourceManagerProvider(instructorFeatureDependenciesParam);
    }

    @Override
    public InstructorRepository provideInstructorRepository() {
      return provideInstructorRepositoryProvider.get();
    }

    @Override
    public InstructorInteractor provideInstructorInteractor() {
      return provideInstructorInteractorProvider.get();
    }

    @Override
    public InstructorsComponent.Factory instructorsComponentFactory() {
      return new InstructorsComponentFactory(instructorFeatureComponentImpl);
    }

    @Override
    public InstructorComponent.Factory instructorComponentFactory() {
      return new InstructorComponentFactory(instructorFeatureComponentImpl);
    }

    private static final class NetworkApiCreatorProvider implements Provider<NetworkApiCreator> {
      private final InstructorFeatureDependencies instructorFeatureDependencies;

      NetworkApiCreatorProvider(InstructorFeatureDependencies instructorFeatureDependencies) {
        this.instructorFeatureDependencies = instructorFeatureDependencies;
      }

      @Override
      public NetworkApiCreator get() {
        return Preconditions.checkNotNullFromComponent(instructorFeatureDependencies.networkApiCreator());
      }
    }

    private static final class DbProvider implements Provider<AppDatabase> {
      private final InstructorFeatureDependencies instructorFeatureDependencies;

      DbProvider(InstructorFeatureDependencies instructorFeatureDependencies) {
        this.instructorFeatureDependencies = instructorFeatureDependencies;
      }

      @Override
      public AppDatabase get() {
        return Preconditions.checkNotNullFromComponent(instructorFeatureDependencies.db());
      }
    }

    private static final class ResourceManagerProvider implements Provider<ResourceManager> {
      private final InstructorFeatureDependencies instructorFeatureDependencies;

      ResourceManagerProvider(InstructorFeatureDependencies instructorFeatureDependencies) {
        this.instructorFeatureDependencies = instructorFeatureDependencies;
      }

      @Override
      public ResourceManager get() {
        return Preconditions.checkNotNullFromComponent(instructorFeatureDependencies.resourceManager());
      }
    }
  }
}
