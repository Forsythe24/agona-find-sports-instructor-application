package com.solopov.feature_instructor_impl.presentation.details;

import com.solopov.common.core.resources.ResourceManager;
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class InstructorViewModel_Factory implements Factory<InstructorViewModel> {
  private final Provider<InstructorInteractor> interactorProvider;

  private final Provider<String> isntructorIdProvider;

  private final Provider<ResourceManager> resourceManagerProvider;

  public InstructorViewModel_Factory(Provider<InstructorInteractor> interactorProvider,
      Provider<String> isntructorIdProvider, Provider<ResourceManager> resourceManagerProvider) {
    this.interactorProvider = interactorProvider;
    this.isntructorIdProvider = isntructorIdProvider;
    this.resourceManagerProvider = resourceManagerProvider;
  }

  @Override
  public InstructorViewModel get() {
    return newInstance(interactorProvider.get(), isntructorIdProvider.get(), resourceManagerProvider.get());
  }

  public static InstructorViewModel_Factory create(
      Provider<InstructorInteractor> interactorProvider, Provider<String> isntructorIdProvider,
      Provider<ResourceManager> resourceManagerProvider) {
    return new InstructorViewModel_Factory(interactorProvider, isntructorIdProvider, resourceManagerProvider);
  }

  public static InstructorViewModel newInstance(InstructorInteractor interactor,
      String isntructorId, ResourceManager resourceManager) {
    return new InstructorViewModel(interactor, isntructorId, resourceManager);
  }
}
