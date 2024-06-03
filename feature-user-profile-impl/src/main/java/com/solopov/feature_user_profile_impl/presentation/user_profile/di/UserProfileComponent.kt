package com.solopov.feature_user_profile_impl.presentation.user_profile.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_user_profile_impl.presentation.user_profile.UserProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        UserProfileModule::class
    ]
)
@ScreenScope
interface UserProfileComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance fragment: Fragment
        ): UserProfileComponent
    }

    fun inject(fragment: UserProfileFragment)
}
