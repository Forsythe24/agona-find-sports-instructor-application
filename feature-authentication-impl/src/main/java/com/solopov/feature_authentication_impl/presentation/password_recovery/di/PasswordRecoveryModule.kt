package com.solopov.feature_authentication_impl.presentation.password_recovery.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.presentation.login.LogInViewModel
import com.solopov.feature_authentication_impl.presentation.password_recovery.PasswordRecoveryViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module(
    includes = [
        ViewModelModule::class
    ]
)
class PasswordRecoveryModule {

    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): PasswordRecoveryViewModel {
        return ViewModelProvider(fragment, factory)[PasswordRecoveryViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(PasswordRecoveryViewModel::class)
    fun providePasswordRecoveryViewModel(interactor: AuthInteractor, exceptionHandlerDelegate: ExceptionHandlerDelegate, router: AuthRouter): ViewModel {
        return PasswordRecoveryViewModel(interactor, exceptionHandlerDelegate, router)
    }
}
