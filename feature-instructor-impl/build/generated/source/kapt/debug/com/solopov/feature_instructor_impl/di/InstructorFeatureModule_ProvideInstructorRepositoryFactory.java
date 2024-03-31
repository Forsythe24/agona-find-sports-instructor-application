package com.solopov.feature_instructor_impl.di;

import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository;
import com.solopov.feature_instructor_impl.data.repository.InstructorRepositoryImpl;
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
public final class InstructorFeatureModule_ProvideInstructorRepositoryFactory implements Factory<InstructorRepository> {
  private final InstructorFeatureModule module;

  private final Provider<InstructorRepositoryImpl> instructorRepositoryProvider;

  public InstructorFeatureModule_ProvideInstructorRepositoryFactory(InstructorFeatureModule module,
      Provider<InstructorRepositoryImpl> instructorRepositoryProvider) {
    this.module = module;
    this.instructorRepositoryProvider = instructorRepositoryProvider;
  }

  @Override
  public InstructorRepository get() {
    return provideInstructorRepository(module, instructorRepositoryProvider.get());
  }

  public static InstructorFeatureModule_ProvideInstructorRepositoryFactory create(
      InstructorFeatureModule module,
      Provider<InstructorRepositoryImpl> instructorRepositoryProvider) {
    return new InstructorFeatureModule_ProvideInstructorRepositoryFactory(module, instructorRepositoryProvider);
  }

  public static InstructorRepository provideInstructorRepository(InstructorFeatureModule instance,
      InstructorRepositoryImpl instructorRepository) {
    return Preconditions.checkNotNullFromProvides(instance.provideInstructorRepository(instructorRepository));
  }
}
