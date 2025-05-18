package com.solopov.feature_user_profile_impl.presentation.user_profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.feature_user_profile_api.domain.usecase.AddRatingUseCase
import com.solopov.feature_user_profile_api.domain.usecase.DeleteProfileUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetAllInstructorRatingsUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LoadUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LogOutUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UploadProfileImageUseCase
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
        factory: ViewModelProvider.Factory,
    ): UserProfileViewModel {
        return ViewModelProvider(fragment, factory)[UserProfileViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun provideInstructorViewModel(
        userMappers: UserMappers,
        ratingMappers: RatingMappers,
        router: UserProfileRouter,
        resourceManager: ResourceManager,
        loadUserInfoUseCase: LoadUserInfoUseCase,
        getCurrentUserUseCase: GetCurrentUserUseCase,
        deleteProfileUseCase: DeleteProfileUseCase,
        logOutUseCase: LogOutUseCase,
        addRatingUseCase: AddRatingUseCase,
        updateUserInfoUseCase: UpdateUserInfoUseCase,
        getAllInstructorRatingsUseCase: GetAllInstructorRatingsUseCase,
        uploadProfileImageUseCase: UploadProfileImageUseCase
    ): ViewModel {
        return UserProfileViewModel(
            userMappers = userMappers,
            ratingMappers = ratingMappers,
            router = router,
            resourceManager = resourceManager,
            loadUserInfoUseCase = loadUserInfoUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            deleteProfileUseCase = deleteProfileUseCase,
            logOutUseCase = logOutUseCase,
            addRatingUseCase = addRatingUseCase,
            updateUserInfoUseCase = updateUserInfoUseCase,
            getAllInstructorRatingsUseCase = getAllInstructorRatingsUseCase,
            uploadProfileImageUseCase = uploadProfileImageUseCase
        )
    }
}
