package com.solopov.feature_user_profile_impl.di

import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.remote.di.FirebaseApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_user_profile_impl.UserProfileRouter
import javax.inject.Inject


@ApplicationScope
class UserProfileFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: UserProfileRouter
) : FeatureApiHolder(featureContainer) {


    //Помогаем даггеру создать компонент именно с теми зависимостями, которые нам нужны

    override fun initializeDependencies(): Any {
        val userProfileFeatureDependencies = DaggerUserProfileFeatureComponent_UserProfileFeatureDependenciesComponent.builder()
            .firebaseApi(getFeature(FirebaseApi::class.java))
            .dbApi(getFeature(DbApi::class.java))
            .commonApi(commonApi())
            .build()
        return DaggerUserProfileFeatureComponent.builder()
            .withDependencies(userProfileFeatureDependencies)
            .router(router)
            .build()
    }
}
