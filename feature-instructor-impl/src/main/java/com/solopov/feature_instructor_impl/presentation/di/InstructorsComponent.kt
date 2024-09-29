package com.solopov.feature_instructor_impl.presentation.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_instructor_impl.presentation.OneSportInstructorsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        InstructorsModule::class
    ]
)
@ScreenScope
interface InstructorsComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance fragment: Fragment,
        ): InstructorsComponent
    }

    fun inject(fragment: OneSportInstructorsFragment)
}
