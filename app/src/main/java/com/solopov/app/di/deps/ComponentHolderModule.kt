package com.solopov.app.di.deps

import com.solopov.app.App
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.db.di.DbHolder
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.feature_instructor_impl.di.InstructorFeatureHolder
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(InstructorFeatureApi::class)
    @IntoMap
    fun provideUserFeatureHolder(instructorFeatureHolder: InstructorFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(DbApi::class)
    @IntoMap
    fun provideDbFeature(dbHolder: DbHolder): FeatureApiHolder
}
