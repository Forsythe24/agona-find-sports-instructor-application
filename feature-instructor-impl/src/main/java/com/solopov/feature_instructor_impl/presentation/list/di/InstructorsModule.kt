package com.solopov.feature_instructor_impl.presentation.list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_impl.presentation.list.InstructorsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class InstructorsModule {

    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): InstructorsViewModel {
        return ViewModelProvider(fragment, factory)[InstructorsViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(InstructorsViewModel::class)
    fun provideInstructorsViewModel(interactor: InstructorInteractor, resourceManager: ResourceManager): ViewModel {
        return InstructorsViewModel(interactor, resourceManager)
    }
}
