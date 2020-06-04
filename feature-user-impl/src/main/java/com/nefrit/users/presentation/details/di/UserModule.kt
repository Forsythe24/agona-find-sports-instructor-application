package com.nefrit.users.presentation.details.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nefrit.common.di.viewmodel.ViewModelKey
import com.nefrit.common.di.viewmodel.ViewModelModule
import com.nefrit.feature_user_api.domain.interfaces.UserInteractor
import com.nefrit.users.presentation.details.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class UserModule {

    @Provides
    fun provideMainViewModel(activity: AppCompatActivity, factory: ViewModelProvider.Factory): UserViewModel {
        return ViewModelProviders.of(activity, factory).get(UserViewModel::class.java)
    }

    @Provides
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun provideSignInViewModel(interactor: UserInteractor, userId: Int): ViewModel {
        return UserViewModel(interactor, userId)
    }
}