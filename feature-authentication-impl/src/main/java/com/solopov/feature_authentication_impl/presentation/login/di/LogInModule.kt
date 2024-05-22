package com.solopov.feature_authentication_impl.presentation.login.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.presentation.login.LogInViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module(
    includes = [
        ViewModelModule::class
    ]
)
class LogInModule {

    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): LogInViewModel {
        return ViewModelProvider(fragment, factory)[LogInViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    fun provideSignInViewModel(interactor: AuthInteractor, exceptionHandlerDelegate: ExceptionHandlerDelegate, resourceManager: ResourceManager, router: AuthRouter): ViewModel {
        return LogInViewModel(interactor, exceptionHandlerDelegate, resourceManager, router)
    }
}
