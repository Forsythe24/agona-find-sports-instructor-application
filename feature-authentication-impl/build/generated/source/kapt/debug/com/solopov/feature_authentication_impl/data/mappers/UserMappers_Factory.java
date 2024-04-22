package com.solopov.feature_authentication_impl.data.mappers;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class UserMappers_Factory implements Factory<UserMappers> {
  @Override
  public UserMappers get() {
    return newInstance();
  }

  public static UserMappers_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static UserMappers newInstance() {
    return new UserMappers();
  }

  private static final class InstanceHolder {
    private static final UserMappers_Factory INSTANCE = new UserMappers_Factory();
  }
}
