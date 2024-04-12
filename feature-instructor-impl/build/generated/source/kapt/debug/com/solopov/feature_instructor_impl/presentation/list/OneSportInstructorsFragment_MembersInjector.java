package com.solopov.feature_instructor_impl.presentation.list;

import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.feature_instructor_impl.InstructorsRouter;
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class OneSportInstructorsFragment_MembersInjector implements MembersInjector<OneSportInstructorsFragment> {
  private final Provider<InstructorsViewModel> viewModelProvider;

  private final Provider<InstructorsRouter> routerProvider;

  private final Provider<InstructorMappers> mappersProvider;

  public OneSportInstructorsFragment_MembersInjector(
      Provider<InstructorsViewModel> viewModelProvider, Provider<InstructorsRouter> routerProvider,
      Provider<InstructorMappers> mappersProvider) {
    this.viewModelProvider = viewModelProvider;
    this.routerProvider = routerProvider;
    this.mappersProvider = mappersProvider;
  }

  public static MembersInjector<OneSportInstructorsFragment> create(
      Provider<InstructorsViewModel> viewModelProvider, Provider<InstructorsRouter> routerProvider,
      Provider<InstructorMappers> mappersProvider) {
    return new OneSportInstructorsFragment_MembersInjector(viewModelProvider, routerProvider, mappersProvider);
  }

  @Override
  public void injectMembers(OneSportInstructorsFragment instance) {
    BaseFragment_MembersInjector.injectViewModel(instance, viewModelProvider.get());
    injectRouter(instance, routerProvider.get());
    injectMappers(instance, mappersProvider.get());
  }

  @InjectedFieldSignature("com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment.router")
  public static void injectRouter(OneSportInstructorsFragment instance, InstructorsRouter router) {
    instance.router = router;
  }

  @InjectedFieldSignature("com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment.mappers")
  public static void injectMappers(OneSportInstructorsFragment instance,
      InstructorMappers mappers) {
    instance.mappers = mappers;
  }
}
