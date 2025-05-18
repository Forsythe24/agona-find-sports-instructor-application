package com.solopov.feature_chat_impl.di

import com.solopov.common.data.network.di.RemoteApi
import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.presentation.chat.di.ChatComponent
import com.solopov.feature_chat_impl.presentation.chat_list.di.ChatsComponent
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    dependencies = [
        ChatFeatureDependencies::class
    ],
    modules = [
        ChatFeatureModule::class,
        ChatFeatureUseCaseModule::class,
    ]
)
interface ChatFeatureComponent : ChatFeatureApi {

    fun chatComponentFactory(): ChatComponent.Factory
    fun chatsComponentFactory(): ChatsComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(chatRouter: ChatRouter): Builder
        fun withDependencies(deps: ChatFeatureDependencies): Builder

        fun build(): ChatFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            RemoteApi::class
        ]
    )
    interface ChatFeatureDependenciesComponent : ChatFeatureDependencies
}
