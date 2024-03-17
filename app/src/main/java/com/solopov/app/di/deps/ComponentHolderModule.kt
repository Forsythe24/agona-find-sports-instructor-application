package com.solopov.app.di.deps

import com.solopov.app.App
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.db.di.DbHolder
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
    @ClassKey(DbApi::class)
    @IntoMap
    fun provideDbFeature(dbHolder: DbHolder): FeatureApiHolder
}
