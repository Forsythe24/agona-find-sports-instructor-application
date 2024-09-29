package com.solopov.feature_user_profile_impl.presentation.edit_profile.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_user_profile_impl.presentation.edit_profile.EditProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        EditProfileModule::class
    ]
)
@ScreenScope
interface EditProfileComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
        ): EditProfileComponent
    }

    fun inject(fragment: EditProfileFragment)
}
