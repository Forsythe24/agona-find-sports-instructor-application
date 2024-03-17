package com.solopov.app.di.app

import com.solopov.app.navigation.Navigator
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}
