package com.solopov.feature_user_profile_impl.presentation.edit_profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserPasswordUseCase
import com.solopov.feature_user_profile_api.domain.usecase.VerifyCredentialsUseCase
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.edit_profile.EditProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class EditProfileModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory,
    ): EditProfileViewModel {
        return ViewModelProvider(fragment, factory)[EditProfileViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    fun provideEditProfileViewModel(
        userMappers: UserMappers,
        router: UserProfileRouter,
        userDataValidator: UserDataValidator,
        resourceManager: ResourceManager,
        updateUserInfoUseCase: UpdateUserInfoUseCase,
        updateUserPasswordUseCase: UpdateUserPasswordUseCase,
        verifyCredentialsUseCase: VerifyCredentialsUseCase,
    ): ViewModel {
        return EditProfileViewModel(
            userMappers,
            router,
            userDataValidator,
            resourceManager,
            updateUserInfoUseCase = updateUserInfoUseCase,
            updateUserPasswordUseCase = updateUserPasswordUseCase,
            verifyCredentialsUseCase = verifyCredentialsUseCase,
        )
    }
}
