// Generated by Dagger (https://dagger.dev).
package com.solopov.feature_instructor_impl.di;

import com.solopov.common.core.resources.ResourceManager;
import com.solopov.common.data.db.AppDatabase;
import com.solopov.common.data.db.di.DbApi;
import com.solopov.common.data.network.NetworkApiCreator;
import com.solopov.common.di.CommonApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerInstructorFeatureComponent_InstructorFeatureDependenciesComponent {
  private DaggerInstructorFeatureComponent_InstructorFeatureDependenciesComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private CommonApi commonApi;

    private DbApi dbApi;

    private Builder() {
    }

    public Builder commonApi(CommonApi commonApi) {
      this.commonApi = Preconditions.checkNotNull(commonApi);
      return this;
    }

    public Builder dbApi(DbApi dbApi) {
      this.dbApi = Preconditions.checkNotNull(dbApi);
      return this;
    }

    public InstructorFeatureComponent.InstructorFeatureDependenciesComponent build() {
      Preconditions.checkBuilderRequirement(commonApi, CommonApi.class);
      Preconditions.checkBuilderRequirement(dbApi, DbApi.class);
      return new InstructorFeatureDependenciesComponentImpl(commonApi, dbApi);
    }
  }

  private static final class InstructorFeatureDependenciesComponentImpl implements InstructorFeatureComponent.InstructorFeatureDependenciesComponent {
    private final CommonApi commonApi;

    private final DbApi dbApi;

    private final InstructorFeatureDependenciesComponentImpl instructorFeatureDependenciesComponentImpl = this;

    private InstructorFeatureDependenciesComponentImpl(CommonApi commonApiParam, DbApi dbApiParam) {
      this.commonApi = commonApiParam;
      this.dbApi = dbApiParam;

    }

    @Override
    public NetworkApiCreator networkApiCreator() {
      return Preconditions.checkNotNullFromComponent(commonApi.provideNetworkApiCreator());
    }

    @Override
    public AppDatabase db() {
      return Preconditions.checkNotNullFromComponent(dbApi.provideDatabase());
    }

    @Override
    public ResourceManager resourceManager() {
      return Preconditions.checkNotNullFromComponent(commonApi.provideResourceManager());
    }
  }
}