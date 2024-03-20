package com.solopov.feature_instructor_impl.presentation.details.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_impl.presentation.details.InstructorViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class InstructorModule {

    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): InstructorViewModel {
        return ViewModelProvider(fragment, factory)[InstructorViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(InstructorViewModel::class)
    fun provideSignInViewModel(interactor: InstructorInteractor, instructorId: String, resourceManager: ResourceManager): ViewModel {
        return InstructorViewModel(interactor, instructorId, resourceManager)
    }
}
