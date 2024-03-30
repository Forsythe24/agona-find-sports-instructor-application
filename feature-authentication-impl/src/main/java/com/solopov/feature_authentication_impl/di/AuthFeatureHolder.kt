package com.solopov.feature_authentication_impl.di

import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class AuthFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer
) : FeatureApiHolder(featureContainer) {


    //Помогаем даггеру создать компонент именно с теми зависимостями, которые нам нужны

    override fun initializeDependencies(): Any {
        val authFeatureDependencies = DaggerAuthFeatureComponent_AuthFeatureDependenciesComponent.builder()
            .firebaseApi(getFeature(FirebaseApi::class.java))
            .commonApi(commonApi())
            .build()
        return DaggerAuthFeatureComponent.builder()
            .withDependencies(authFeatureDependencies)
            .build()
    }
}
