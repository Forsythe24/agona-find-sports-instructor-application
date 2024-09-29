package com.solopov.feature_user_profile_impl.presentation.instruct.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_user_profile_impl.presentation.instruct.InstructApplicationFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        InstructApplicationModule::class
    ]
)
@ScreenScope
interface InstructApplicationComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
        ): InstructApplicationComponent
    }

    fun inject(fragment: InstructApplicationFragment)
}
