package com.solopov.common.data.db.di

import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.ApplicationScope
import dagger.Component

@Component(
    modules = [
        DbModule::class
    ],
    dependencies = [
        DbDependencies::class
    ]
)
@ApplicationScope
abstract class DbComponent : DbApi {

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface DbDependenciesComponent : DbDependencies
}
