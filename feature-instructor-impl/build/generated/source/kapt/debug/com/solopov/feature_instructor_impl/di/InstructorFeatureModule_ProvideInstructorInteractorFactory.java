package com.solopov.feature_instructor_impl.di;

import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor;
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("com.solopov.common.di.scope.FeatureScope")
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
public final class InstructorFeatureModule_ProvideInstructorInteractorFactory implements Factory<InstructorInteractor> {
  private final InstructorFeatureModule module;

  private final Provider<InstructorRepository> repositoryProvider;

  public InstructorFeatureModule_ProvideInstructorInteractorFactory(InstructorFeatureModule module,
      Provider<InstructorRepository> repositoryProvider) {
    this.module = module;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InstructorInteractor get() {
    return provideInstructorInteractor(module, repositoryProvider.get());
  }

  public static InstructorFeatureModule_ProvideInstructorInteractorFactory create(
      InstructorFeatureModule module, Provider<InstructorRepository> repositoryProvider) {
    return new InstructorFeatureModule_ProvideInstructorInteractorFactory(module, repositoryProvider);
  }

  public static InstructorInteractor provideInstructorInteractor(InstructorFeatureModule instance,
      InstructorRepository repository) {
    return Preconditions.checkNotNullFromProvides(instance.provideInstructorInteractor(repository));
  }
}
