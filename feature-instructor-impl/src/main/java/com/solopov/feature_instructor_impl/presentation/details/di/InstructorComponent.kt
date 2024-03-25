package com.solopov.feature_instructor_impl.presentation.details.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_instructor_impl.presentation.details.InstructorFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        InstructorModule::class
    ]
)
@ScreenScope
interface InstructorComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance instructorId: String
        ): InstructorComponent
    }

    fun inject(fragment: InstructorFragment)
}
