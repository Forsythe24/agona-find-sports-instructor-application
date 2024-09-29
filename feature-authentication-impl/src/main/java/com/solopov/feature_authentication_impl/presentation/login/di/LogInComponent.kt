package com.solopov.feature_authentication_impl.presentation.login.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_authentication_impl.presentation.login.LogInFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        LogInModule::class
    ]
)
@ScreenScope
interface LogInComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
        ): LogInComponent
    }

    fun inject(fragment: LogInFragment)
}
