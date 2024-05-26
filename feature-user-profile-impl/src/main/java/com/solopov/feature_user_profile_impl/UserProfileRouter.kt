package com.solopov.feature_user_profile_impl

import com.solopov.common.model.ChatCommon
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile

interface UserProfileRouter {
    fun goBackToInstructors()
    fun goToInstructApplication(userProfile: UserProfile)
    fun goBack()
    fun goToEditingProfile(userProfile: UserProfile)
    fun goFromUserProfileToChat(chat: ChatCommon)
}
