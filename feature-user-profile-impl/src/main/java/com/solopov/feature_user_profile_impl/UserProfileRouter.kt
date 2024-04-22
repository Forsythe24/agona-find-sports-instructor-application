package com.solopov.feature_user_profile_impl

import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile

interface UserProfileRouter {
    fun goBackToInstructors()

    fun goToInstructApplication(userProfile: UserProfile)

    fun goToEditingProfile(userProfile: UserProfile)
}