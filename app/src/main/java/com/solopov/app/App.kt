package com.solopov.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.solopov.app.di.app.AppComponent
import com.solopov.app.di.deps.ComponentDependenciesProvider
import com.solopov.app.di.deps.FeatureHolderManager
import com.solopov.common.di.CommonApi
import com.solopov.common.di.FeatureContainer
import javax.inject.Inject

open class App : Application(), FeatureContainer {

    @Inject lateinit var featureHolderManager: FeatureHolderManager

    @Inject lateinit var dependencies: ComponentDependenciesProvider

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

    override fun <T> getFeature(key: Class<*>): T {
        return featureHolderManager.getFeature<T>(key)!!
    }

    override fun releaseFeature(key: Class<*>) {
        featureHolderManager.releaseFeature(key)
    }

    override fun commonApi(): CommonApi {
        return appComponent
    }
}
