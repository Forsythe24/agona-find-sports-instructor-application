// Generated by Dagger (https://dagger.dev).
package com.solopov.feature_instructor_impl.data.mappers;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class InstructorMappers_Factory implements Factory<InstructorMappers> {
  @Override
  public InstructorMappers get() {
    return newInstance();
  }

  public static InstructorMappers_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static InstructorMappers newInstance() {
    return new InstructorMappers();
  }

  private static final class InstanceHolder {
    private static final InstructorMappers_Factory INSTANCE = new InstructorMappers_Factory();
  }
}
