package com.solopov.app.di.app

import com.solopov.app.App
import com.solopov.app.di.deps.ComponentDependenciesModule
import com.solopov.app.di.deps.ComponentHolderModule
import com.solopov.app.di.main.MainDependencies
import com.solopov.common.di.CommonApi
import com.solopov.common.di.coroutine.CoroutineModule
import com.solopov.common.di.modules.CommonModule
import com.solopov.common.di.modules.NetworkModule
import com.solopov.common.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        CommonModule::class,
        NetworkModule::class,
        NavigationModule::class,
        ComponentHolderModule::class,
        ComponentDependenciesModule::class,
        FeatureManagerModule::class,
        CoroutineModule::class,
    ]
)
interface AppComponent : MainDependencies, CommonApi {

    companion object {

        fun init(application: App): AppComponent {
            return DaggerAppComponent
                .builder()
                .application(application)
                .build()
        }
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
