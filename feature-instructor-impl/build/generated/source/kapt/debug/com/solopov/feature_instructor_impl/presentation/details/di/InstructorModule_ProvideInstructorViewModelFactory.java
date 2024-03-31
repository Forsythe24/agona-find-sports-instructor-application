package com.solopov.feature_instructor_impl.presentation.details.di;

import androidx.lifecycle.ViewModel;
import com.solopov.common.core.resources.ResourceManager;
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor;
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
public final class InstructorModule_ProvideInstructorViewModelFactory implements Factory<ViewModel> {
  private final InstructorModule module;

  private final Provider<InstructorInteractor> interactorProvider;

  private final Provider<String> instructorIdProvider;

  private final Provider<ResourceManager> resourceManagerProvider;

  public InstructorModule_ProvideInstructorViewModelFactory(InstructorModule module,
      Provider<InstructorInteractor> interactorProvider, Provider<String> instructorIdProvider,
      Provider<ResourceManager> resourceManagerProvider) {
    this.module = module;
    this.interactorProvider = interactorProvider;
    this.instructorIdProvider = instructorIdProvider;
    this.resourceManagerProvider = resourceManagerProvider;
  }

  @Override
  public ViewModel get() {
    return provideInstructorViewModel(module, interactorProvider.get(), instructorIdProvider.get(), resourceManagerProvider.get());
  }

  public static InstructorModule_ProvideInstructorViewModelFactory create(InstructorModule module,
      Provider<InstructorInteractor> interactorProvider, Provider<String> instructorIdProvider,
      Provider<ResourceManager> resourceManagerProvider) {
    return new InstructorModule_ProvideInstructorViewModelFactory(module, interactorProvider, instructorIdProvider, resourceManagerProvider);
  }

  public static ViewModel provideInstructorViewModel(InstructorModule instance,
      InstructorInteractor interactor, String instructorId, ResourceManager resourceManager) {
    return Preconditions.checkNotNullFromProvides(instance.provideInstructorViewModel(interactor, instructorId, resourceManager));
  }
}
