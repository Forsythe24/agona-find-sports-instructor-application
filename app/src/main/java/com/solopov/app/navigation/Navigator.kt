package com.solopov.app.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.solopov.app.R
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.ParamsKey.CHAT_KEY
import com.solopov.common.utils.ParamsKey.FROM_INSTRUCTORS_SCREEN_FLAG_KEY
import com.solopov.common.utils.ParamsKey.PARTNER_NAME_KEY
import com.solopov.common.utils.ParamsKey.USER_ID_KEY
import com.solopov.common.utils.ParamsKey.USER_KEY
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile

class Navigator : InstructorsRouter, AuthRouter, UserProfileRouter, ChatRouter, EventCalendarRouter {

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

    override fun goFromChatToEventCalendar(partnerName: String) {
        navController?.navigate(R.id.action_chatFragment_to_eventCalendarFragment, bundleOf(PARTNER_NAME_KEY to partnerName))
    }

    override fun openInstructor(userId: String) {
        navController?.navigate(R.id.userProfileFragment, bundleOf(USER_ID_KEY to userId, FROM_INSTRUCTORS_SCREEN_FLAG_KEY to true))
    }

    override fun goToSignUp() {
        navController?.navigate(R.id.signUpFragment)
    }

    override fun goToInstructorsList() {
        navController?.navigate(R.id.instructorsFragment)
    }

    override fun goFromLogInToUserProfile() {
        navController?.navigate(R.id.action_logInFragment_to_userProfileFragment)
    }

    override fun goFromSignUpToInstructors() {
        navController?.navigate(R.id.action_signUpFragment_to_instructorsFragment)
    }

    override fun goToPasswordRecovery() {
        navController?.navigate(R.id.action_logInFragment_to_passwordRecoveryFragment)
    }

    override fun goBackToInstructors() {
        navController?.navigate(R.id.instructorsFragment)
    }

    override fun goToInstructApplication(userProfile: UserProfile) {
        navController?.navigate(R.id.instructApplicationFragment, bundleOf(USER_KEY to userProfile))
    }

    override fun goBackToChats() {
        navController?.navigate(R.id.chatsFragment)
    }

    override fun goFromChatsToChat(chat: ChatCommon) {
        navController?.navigate(R.id.action_chatsFragment_to_chatFragment, bundleOf(CHAT_KEY to chat))
    }

    override fun goToEditingProfile(userProfile: UserProfile) {
        navController?.navigate(R.id.editProfileFragment, bundleOf(USER_KEY to userProfile))
    }

    override fun goFromUserProfileToChat(chat: ChatCommon) {
        navController?.navigate(R.id.action_userProfileFragment_to_chatFragment, bundleOf(CHAT_KEY to chat))
    }

    override fun goFromUserProfileToLogInScreen() {
        navController?.navigate(R.id.action_userProfileFragment_to_logInFragment)

    }

    override fun goBack() {
        navController?.popBackStack()
    }
}
