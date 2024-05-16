package com.solopov.common.data.remote.di

import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class RemoteHolder @Inject constructor(
    featureContainer: FeatureContainer
) : FeatureApiHolder(featureContainer){
    override fun initializeDependencies(): Any {
        val remoteDependencies = DaggerRemoteComponent_RemoteDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerRemoteComponent.builder()
            .remoteDependencies(remoteDependencies)
            .build()
    }
}
