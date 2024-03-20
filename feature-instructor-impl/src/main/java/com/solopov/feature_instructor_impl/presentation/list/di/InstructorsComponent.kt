package com.solopov.feature_instructor_impl.presentation.list.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_instructor_impl.presentation.list.InstructorsFragment
import com.solopov.users.presentation.list.di.InstructorsModule
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
            @BindsInstance fragment: Fragment
        ): InstructorsComponent
    }

    fun inject(fragment: InstructorsFragment)
}
