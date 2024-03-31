package com.solopov.feature_instructor_impl.presentation.details;

import com.solopov.common.base.BaseFragment_MembersInjector;
import com.solopov.feature_instructor_impl.InstructorsRouter;
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
public final class InstructorFragment_MembersInjector implements MembersInjector<InstructorFragment> {
  private final Provider<InstructorViewModel> viewModelProvider;

  private final Provider<InstructorsRouter> routerProvider;

  public InstructorFragment_MembersInjector(Provider<InstructorViewModel> viewModelProvider,
      Provider<InstructorsRouter> routerProvider) {
    this.viewModelProvider = viewModelProvider;
    this.routerProvider = routerProvider;
  }

  public static MembersInjector<InstructorFragment> create(
      Provider<InstructorViewModel> viewModelProvider, Provider<InstructorsRouter> routerProvider) {
    return new InstructorFragment_MembersInjector(viewModelProvider, routerProvider);
  }

  @Override
  public void injectMembers(InstructorFragment instance) {
    BaseFragment_MembersInjector.injectViewModel(instance, viewModelProvider.get());
    injectRouter(instance, routerProvider.get());
  }

  @InjectedFieldSignature("com.solopov.feature_instructor_impl.presentation.details.InstructorFragment.router")
  public static void injectRouter(InstructorFragment instance, InstructorsRouter router) {
    instance.router = router;
  }
}
