package com.solopov.feature_user_profile_impl.presentation.instruct.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.instruct.InstructApplicationViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class InstructApplicationModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): InstructApplicationViewModel {
        return ViewModelProvider(fragment, factory)[InstructApplicationViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(InstructApplicationViewModel::class)
    fun provideInstructApplicationViewModel(
        interactor: UserProfileInteractor,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        userMappers: UserMappers,
        router: UserProfileRouter,
        resManager: ResourceManager
    ): ViewModel {
        return InstructApplicationViewModel(
            interactor,
            exceptionHandlerDelegate,
            userMappers,
            router,
            resManager
        )
    }
}
