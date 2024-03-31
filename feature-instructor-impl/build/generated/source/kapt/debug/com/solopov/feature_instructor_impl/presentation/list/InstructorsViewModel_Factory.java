package com.solopov.feature_instructor_impl.presentation.list;

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
public final class InstructorsViewModel_Factory implements Factory<InstructorsViewModel> {
  private final Provider<InstructorInteractor> interactorProvider;

  private final Provider<ResourceManager> resourceManagerProvider;

  public InstructorsViewModel_Factory(Provider<InstructorInteractor> interactorProvider,
      Provider<ResourceManager> resourceManagerProvider) {
    this.interactorProvider = interactorProvider;
    this.resourceManagerProvider = resourceManagerProvider;
  }

  @Override
  public InstructorsViewModel get() {
    return newInstance(interactorProvider.get(), resourceManagerProvider.get());
  }

  public static InstructorsViewModel_Factory create(
      Provider<InstructorInteractor> interactorProvider,
      Provider<ResourceManager> resourceManagerProvider) {
    return new InstructorsViewModel_Factory(interactorProvider, resourceManagerProvider);
  }

  public static InstructorsViewModel newInstance(InstructorInteractor interactor,
      ResourceManager resourceManager) {
    return new InstructorsViewModel(interactor, resourceManager);
  }
}
