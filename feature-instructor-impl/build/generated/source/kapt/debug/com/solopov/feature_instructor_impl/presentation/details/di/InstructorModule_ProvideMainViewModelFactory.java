package com.solopov.feature_instructor_impl.presentation.details.di;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.solopov.feature_instructor_impl.presentation.details.InstructorViewModel;
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
public final class InstructorModule_ProvideMainViewModelFactory implements Factory<InstructorViewModel> {
  private final InstructorModule module;

  private final Provider<Fragment> fragmentProvider;

  private final Provider<ViewModelProvider.Factory> factoryProvider;

  public InstructorModule_ProvideMainViewModelFactory(InstructorModule module,
      Provider<Fragment> fragmentProvider, Provider<ViewModelProvider.Factory> factoryProvider) {
    this.module = module;
    this.fragmentProvider = fragmentProvider;
    this.factoryProvider = factoryProvider;
  }

  @Override
  public InstructorViewModel get() {
    return provideMainViewModel(module, fragmentProvider.get(), factoryProvider.get());
  }

  public static InstructorModule_ProvideMainViewModelFactory create(InstructorModule module,
      Provider<Fragment> fragmentProvider, Provider<ViewModelProvider.Factory> factoryProvider) {
    return new InstructorModule_ProvideMainViewModelFactory(module, fragmentProvider, factoryProvider);
  }

  public static InstructorViewModel provideMainViewModel(InstructorModule instance,
      Fragment fragment, ViewModelProvider.Factory factory) {
    return Preconditions.checkNotNullFromProvides(instance.provideMainViewModel(fragment, factory));
  }
}
