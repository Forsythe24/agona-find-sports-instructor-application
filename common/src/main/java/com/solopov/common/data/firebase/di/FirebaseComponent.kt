package com.solopov.common.data.firebase.di

import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.ApplicationScope
import dagger.Component

@Component(
    modules = [
        FirebaseModule::class
    ],
    dependencies = [
        FirebaseDependencies::class
    ]
)
@ApplicationScope
abstract class FirebaseComponent: FirebaseApi {

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface FirebaseDependenciesComponent : FirebaseDependencies
}
