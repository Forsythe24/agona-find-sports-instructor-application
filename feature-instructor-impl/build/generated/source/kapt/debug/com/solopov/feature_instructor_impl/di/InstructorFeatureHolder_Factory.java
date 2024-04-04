package com.solopov.feature_instructor_impl.di;

import com.solopov.common.di.FeatureContainer;
import com.solopov.feature_instructor_impl.InstructorsRouter;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("com.solopov.common.di.scope.ApplicationScope")
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
public final class InstructorFeatureHolder_Factory implements Factory<InstructorFeatureHolder> {
  private final Provider<FeatureContainer> featureContainerProvider;

  private final Provider<InstructorsRouter> instructorsRouterProvider;

  public InstructorFeatureHolder_Factory(Provider<FeatureContainer> featureContainerProvider,
      Provider<InstructorsRouter> instructorsRouterProvider) {
    this.featureContainerProvider = featureContainerProvider;
    this.instructorsRouterProvider = instructorsRouterProvider;
  }

  @Override
  public InstructorFeatureHolder get() {
    return newInstance(featureContainerProvider.get(), instructorsRouterProvider.get());
  }

  public static InstructorFeatureHolder_Factory create(
      Provider<FeatureContainer> featureContainerProvider,
      Provider<InstructorsRouter> instructorsRouterProvider) {
    return new InstructorFeatureHolder_Factory(featureContainerProvider, instructorsRouterProvider);
  }

  public static InstructorFeatureHolder newInstance(FeatureContainer featureContainer,
      InstructorsRouter instructorsRouter) {
    return new InstructorFeatureHolder(featureContainer, instructorsRouter);
  }
}
