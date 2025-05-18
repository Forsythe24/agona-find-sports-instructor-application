package com.solopov.feature_user_profile_impl.di

import com.solopov.feature_user_profile_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_user_profile_api.domain.usecase.AddRatingUseCase
import com.solopov.feature_user_profile_api.domain.usecase.DeleteProfileUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetAllInstructorRatingsUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetRatingByUserAndInstructorUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LoadUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LogOutUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserPasswordUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UploadProfileImageUseCase
import com.solopov.feature_user_profile_api.domain.usecase.VerifyCredentialsUseCase
import com.solopov.feature_user_profile_impl.domain.usecase.AddRatingUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.DeleteProfileUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.GetAllInstructorRatingsUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.GetCurrentUserUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.GetRatingByUserAndInstructorIdsUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.LoadUserInfoUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.LogOutUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.UpdateUserInfoUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.UpdateUserPasswordUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.UploadProfileImageUseCaseImpl
import com.solopov.feature_user_profile_impl.domain.usecase.VerifyCredentialsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UserProfileFeatureUseCaseModule {
    @Binds
    fun bindUploadProfileImageUseCase(impl: UploadProfileImageUseCaseImpl): UploadProfileImageUseCase

    @Binds
    fun bindAddRatingUseCase(impl: AddRatingUseCaseImpl): AddRatingUseCase

    @Binds
    fun bindGetAllInstructorRatingsByIdUseCase(impl: GetAllInstructorRatingsUseCaseImpl): GetAllInstructorRatingsUseCase

    @Binds
    fun bindGetRatingByUserAndInstructorIdsUseCase(impl: GetRatingByUserAndInstructorIdsUseCaseImpl): GetRatingByUserAndInstructorUseCase

    @Binds
    fun bindUpdateUserInfoUseCase(impl: UpdateUserInfoUseCase): UpdateUserInfoUseCaseImpl

    @Binds
    fun bindDeleteProfileUseCase(impl: DeleteProfileUseCaseImpl): DeleteProfileUseCase

    @Binds
    fun bindLogOutUseCase(impl: LogOutUseCaseImpl): LogOutUseCase

    @Binds
    fun bindGetCurrentUserUseCase(impl: GetCurrentUserUseCaseImpl): GetCurrentUserUseCase

    @Binds
    fun bindLoadUserInfoUseCase(impl: LoadUserInfoUseCaseImpl): LoadUserInfoUseCase

    @Binds
    fun bindVerifyCredentialsUseCase(impl: VerifyCredentialsUseCaseImpl): VerifyCredentialsUseCase

    @Binds
    fun bindUpdateUserPassword(impl: UpdateUserPasswordUseCaseImpl): UpdateUserPasswordUseCase
}
