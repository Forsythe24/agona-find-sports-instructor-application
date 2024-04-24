package com.solopov.app.di.app

import com.solopov.app.navigation.Navigator
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_user_profile_impl.UserProfileRouter
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideInstructorsRouter(navigator: Navigator): InstructorsRouter = navigator

    @ApplicationScope
    @Provides
    fun provideAuthRouter(navigator: Navigator): AuthRouter = navigator

    @ApplicationScope
    @Provides
    fun provideUserProfileRouter(navigator: Navigator): UserProfileRouter = navigator

    @ApplicationScope
    @Provides
    fun provideChatRouter(navigator: Navigator): ChatRouter = navigator
}
