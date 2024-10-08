package com.solopov.app.di.main

import androidx.appcompat.app.AppCompatActivity
import com.solopov.app.MainActivity
import com.solopov.common.di.scope.ScreenScope
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [
        MainDependencies::class
    ],
    modules = [
        MainModule::class
    ]
)
@ScreenScope
interface MainComponent {

    companion object {

        fun init(activity: AppCompatActivity, deps: MainDependencies): MainComponent {
            return DaggerMainComponent.factory().create(activity, deps)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: AppCompatActivity,
            deps: MainDependencies,
        ): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}
