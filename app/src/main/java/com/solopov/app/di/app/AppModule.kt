package com.solopov.app.di.app

import android.content.Context
import com.solopov.app.App
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @ApplicationScope
    @Provides
    fun provideContext(application: App): Context {
        return application
    }
}
