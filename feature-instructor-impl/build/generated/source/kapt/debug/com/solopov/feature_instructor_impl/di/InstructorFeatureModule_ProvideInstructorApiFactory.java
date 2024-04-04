package com.solopov.feature_instructor_impl.di;

import com.solopov.common.data.network.NetworkApiCreator;
import com.solopov.feature_instructor_impl.data.network.InstructorApi;
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
public final class InstructorFeatureModule_ProvideInstructorApiFactory implements Factory<InstructorApi> {
  private final InstructorFeatureModule module;

  private final Provider<NetworkApiCreator> apiCreatorProvider;

  public InstructorFeatureModule_ProvideInstructorApiFactory(InstructorFeatureModule module,
      Provider<NetworkApiCreator> apiCreatorProvider) {
    this.module = module;
    this.apiCreatorProvider = apiCreatorProvider;
  }

  @Override
  public InstructorApi get() {
    return provideInstructorApi(module, apiCreatorProvider.get());
  }

  public static InstructorFeatureModule_ProvideInstructorApiFactory create(
      InstructorFeatureModule module, Provider<NetworkApiCreator> apiCreatorProvider) {
    return new InstructorFeatureModule_ProvideInstructorApiFactory(module, apiCreatorProvider);
  }

  public static InstructorApi provideInstructorApi(InstructorFeatureModule instance,
      NetworkApiCreator apiCreator) {
    return Preconditions.checkNotNullFromProvides(instance.provideInstructorApi(apiCreator));
  }
}
