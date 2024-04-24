package com.solopov.feature_chat_impl.di

import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_chat_impl.ChatRouter
import javax.inject.Inject

@ApplicationScope
class ChatFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val chatRouter: ChatRouter
) : FeatureApiHolder(featureContainer) {


    override fun initializeDependencies(): Any {
        val chatFeatureDependencies = DaggerChatFeatureComponent_ChatFeatureDependenciesComponent.builder()
            .firebaseApi(getFeature(FirebaseApi::class.java))
            .commonApi(commonApi())
            .build()
        return DaggerChatFeatureComponent.builder()
            .withDependencies(chatFeatureDependencies)
            .router(chatRouter)
            .build()
    }
}