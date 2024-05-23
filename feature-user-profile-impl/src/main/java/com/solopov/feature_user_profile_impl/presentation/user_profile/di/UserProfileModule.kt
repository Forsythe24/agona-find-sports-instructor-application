package com.solopov.feature_user_profile_impl.presentation.user_profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.RatingMappers
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.UserProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class UserProfileModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): UserProfileViewModel {
        return ViewModelProvider(fragment, factory)[UserProfileViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun provideInstructorViewModel(
        interactor: UserProfileInteractor,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        userMappers: UserMappers,
        ratingMappers: RatingMappers,
        router: UserProfileRouter
    ): ViewModel {
        return UserProfileViewModel(
            interactor,
            exceptionHandlerDelegate,
            userMappers,
            ratingMappers,
            router
        )
    }
}
