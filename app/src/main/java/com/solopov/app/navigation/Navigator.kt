package com.solopov.app.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.app.R
import com.solopov.common.model.ChatCommon
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.common.utils.ParamsKey.CHAT
import com.solopov.common.utils.ParamsKey.FROM_INSTRUCTORS_SCREEN_FLAG_KEY
import com.solopov.common.utils.ParamsKey.USER
import com.solopov.common.utils.ParamsKey.USER_ID_KEY
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile

class Navigator : InstructorsRouter, AuthRouter, UserProfileRouter, ChatRouter {

    private var navController: NavController? = null

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    fun detachNavController(navController: NavController) {
        if (this.navController == navController) {
            this.navController = null
        }
    }

    override fun openUserProfile(userId: String) {
        navController?.navigate(R.id.userProfileFragment, bundleOf(USER_ID_KEY to userId))
    }
    override fun openInstructor(userId: String) {
        navController?.navigate(R.id.userProfileFragment, bundleOf(USER_ID_KEY to userId, FROM_INSTRUCTORS_SCREEN_FLAG_KEY to true))
    }

    override fun returnToInstructors() {
        navController?.popBackStack()
    }

    override fun openChat(chat: ChatCommon) {
        navController?.navigate(R.id.chatFragment, bundleOf(CHAT to chat))
    }

    override fun goToSignUpPage() {
        navController?.navigate(R.id.signUpFragment)
    }

    override fun goToInstructorsList() {
        navController?.navigate(R.id.instructorsFragment)
    }

    override fun goToUserProfile() {
        navController?.navigate(R.id.userProfileFragment)
    }

    override fun goBackToInstructors() {
        navController?.navigate(R.id.instructorsFragment)
    }

    override fun goToInstructApplication(userProfile: UserProfile) {
        navController?.navigate(R.id.instructApplicationFragment, bundleOf(USER to userProfile))
    }

    override fun goBack() {
        navController?.popBackStack()
    }

    override fun goToEditingProfile(userProfile: UserProfile) {
        navController?.navigate(R.id.editProfileFragment, bundleOf(USER to userProfile))
    }
}
