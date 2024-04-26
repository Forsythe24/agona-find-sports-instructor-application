package com.solopov.feature_instructor_impl.data.repository;

import com.solopov.common.core.resources.ResourceManager;
import com.solopov.common.data.db.AppDatabase;
import com.solopov.common.data.firebase.dao.UserFirebaseDao;
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers;
import com.solopov.feature_instructor_impl.data.network.InstructorApi;
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
public final class InstructorRepositoryImpl_Factory implements Factory<InstructorRepositoryImpl> {
  private final Provider<InstructorApi> apiProvider;

  private final Provider<AppDatabase> dbProvider;

  private final Provider<InstructorMappers> instructorMappersProvider;

  private final Provider<UserFirebaseDao> userFirebaseDaoProvider;

  private final Provider<ResourceManager> resManagerProvider;

  public InstructorRepositoryImpl_Factory(Provider<InstructorApi> apiProvider,
      Provider<AppDatabase> dbProvider, Provider<InstructorMappers> instructorMappersProvider,
      Provider<UserFirebaseDao> userFirebaseDaoProvider,
      Provider<ResourceManager> resManagerProvider) {
    this.apiProvider = apiProvider;
    this.dbProvider = dbProvider;
    this.instructorMappersProvider = instructorMappersProvider;
    this.userFirebaseDaoProvider = userFirebaseDaoProvider;
    this.resManagerProvider = resManagerProvider;
  }

  @Override
  public InstructorRepositoryImpl get() {
    return newInstance(apiProvider.get(), dbProvider.get(), instructorMappersProvider.get(), userFirebaseDaoProvider.get(), resManagerProvider.get());
  }

  public static InstructorRepositoryImpl_Factory create(Provider<InstructorApi> apiProvider,
      Provider<AppDatabase> dbProvider, Provider<InstructorMappers> instructorMappersProvider,
      Provider<UserFirebaseDao> userFirebaseDaoProvider,
      Provider<ResourceManager> resManagerProvider) {
    return new InstructorRepositoryImpl_Factory(apiProvider, dbProvider, instructorMappersProvider, userFirebaseDaoProvider, resManagerProvider);
  }

  public static InstructorRepositoryImpl newInstance(InstructorApi api, AppDatabase db,
      InstructorMappers instructorMappers, UserFirebaseDao userFirebaseDao,
      ResourceManager resManager) {
    return new InstructorRepositoryImpl(api, db, instructorMappers, userFirebaseDao, resManager);
  }
}
