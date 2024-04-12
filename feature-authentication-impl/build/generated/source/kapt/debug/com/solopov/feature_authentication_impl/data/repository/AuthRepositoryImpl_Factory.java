package com.solopov.feature_authentication_impl.data.repository;

import com.solopov.common.data.firebase.dao.UserFirebaseDao;
import com.solopov.feature_authentication_impl.data.mappers.UserMappers;
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<UserFirebaseDao> userFirebaseDaoProvider;

  private final Provider<UserMappers> userMappersProvider;

  public AuthRepositoryImpl_Factory(Provider<UserFirebaseDao> userFirebaseDaoProvider,
      Provider<UserMappers> userMappersProvider) {
    this.userFirebaseDaoProvider = userFirebaseDaoProvider;
    this.userMappersProvider = userMappersProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(userFirebaseDaoProvider.get(), userMappersProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<UserFirebaseDao> userFirebaseDaoProvider,
      Provider<UserMappers> userMappersProvider) {
    return new AuthRepositoryImpl_Factory(userFirebaseDaoProvider, userMappersProvider);
  }

  public static AuthRepositoryImpl newInstance(UserFirebaseDao userFirebaseDao,
      UserMappers userMappers) {
    return new AuthRepositoryImpl(userFirebaseDao, userMappers);
  }
}
