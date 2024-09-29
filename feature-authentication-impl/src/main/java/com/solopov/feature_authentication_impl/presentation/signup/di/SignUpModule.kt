package com.solopov.feature_authentication_impl.presentation.signup.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module(
    includes = [
        ViewModelModule::class
    ]
)
class SignUpModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory,
    ): SignUpViewModel {
        return ViewModelProvider(fragment, factory)[SignUpViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    fun provideSignInViewModel(
        interactor: AuthInteractor,
        authRouter: AuthRouter,
        validator: UserDataValidator,
        resourceManager: ResourceManager,
    ): ViewModel {
        return SignUpViewModel(interactor, authRouter, validator, resourceManager)
    }
}
