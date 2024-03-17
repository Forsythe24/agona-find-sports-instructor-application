package com.solopov.app.di.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.solopov.app.MainViewModel
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideViewModel(): ViewModel {
        return MainViewModel()
    }

    @Provides
    fun provideViewModelCreator(activity: AppCompatActivity, viewModelFactory: ViewModelProvider.Factory): MainViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(MainViewModel::class.java)
    }
}
