package com.solopov.common.data.firebase.di

import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class FirebaseHolder @Inject constructor(
    featureContainer: FeatureContainer
) : FeatureApiHolder(featureContainer){
    override fun initializeDependencies(): Any {
        val firebaseDependencies = DaggerFirebaseComponent_FirebaseDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerFirebaseComponent.builder()
            .firebaseDependencies(firebaseDependencies)
            .build()
    }
}
