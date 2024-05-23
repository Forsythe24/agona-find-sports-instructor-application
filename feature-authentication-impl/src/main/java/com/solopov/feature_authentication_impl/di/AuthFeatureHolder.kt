package com.solopov.feature_authentication_impl.di

import com.solopov.common.data.remote.di.RemoteApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_authentication_impl.AuthRouter
import javax.inject.Inject

@ApplicationScope
class AuthFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val authRouter: AuthRouter
) : FeatureApiHolder(featureContainer) {


    //Помогаем даггеру создать компонент именно с теми зависимостями, которые нам нужны

    override fun initializeDependencies(): Any {
        val authFeatureDependencies =
            DaggerAuthFeatureComponent_AuthFeatureDependenciesComponent.builder()
                .remoteApi(getFeature(RemoteApi::class.java))
                .commonApi(commonApi())
                .build()
        return DaggerAuthFeatureComponent.builder()
            .withDependencies(authFeatureDependencies)
            .router(authRouter)
            .build()
    }
}
