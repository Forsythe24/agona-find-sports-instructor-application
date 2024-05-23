package com.solopov.common.data.remote.di

import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.ApplicationScope
import dagger.Component

@Component(
    modules = [
        RemoteModule::class
    ],
    dependencies = [
        RemoteDependencies::class
    ]
)
@ApplicationScope
abstract class RemoteComponent : RemoteApi {

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface RemoteDependenciesComponent : RemoteDependencies
}
