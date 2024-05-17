package com.solopov.feature_authentication_impl.presentation.password_recovery.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_authentication_impl.presentation.login.LogInFragment
import com.solopov.feature_authentication_impl.presentation.password_recovery.PasswordRecoveryFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        PasswordRecoveryModule::class
    ]
)
@ScreenScope
interface PasswordRecoveryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): PasswordRecoveryComponent
    }

    fun inject(fragment: PasswordRecoveryFragment)
}
