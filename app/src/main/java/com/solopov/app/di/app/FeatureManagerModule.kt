package com.solopov.app.di.app

import com.solopov.app.di.deps.FeatureHolderManager
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class FeatureManagerModule {

    @ApplicationScope
    @Provides
    fun provideFeatureHolderManager(featureApiHolderMap: @JvmSuppressWildcards Map<Class<*>, FeatureApiHolder>): FeatureHolderManager {
        return FeatureHolderManager(featureApiHolderMap)
    }
}
