package com.solopov.app.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics.Param
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.app.R
import com.solopov.common.model.UserCommon
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.presentation.user_profile.UserProfileFragment
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile

class Navigator : InstructorsRouter, AuthRouter, UserProfileRouter {

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

    override fun openInstructor(instructorId: String) {
        navController?.navigate(R.id.userProfileFragment, bundleOf(ParamsKey.KEY_INSTRUCTOR_ID to instructorId))
    }
    override fun openInstructor(instructor: UserCommon) {
        navController?.navigate(R.id.userProfileFragment, bundleOf(ParamsKey.USER to instructor))
    }

    override fun returnToInstructors() {
        navController?.popBackStack()
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
        navController?.navigate(R.id.instructApplicationFragment, bundleOf(ParamsKey.USER to userProfile))
    }



}
