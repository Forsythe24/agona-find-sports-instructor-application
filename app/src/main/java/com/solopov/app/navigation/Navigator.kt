package com.solopov.app.navigation

import androidx.navigation.NavController
import com.solopov.app.R
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_instructor_impl.InstructorsRouter

class Navigator : InstructorsRouter, AuthRouter,  {

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
        TODO("Not yet implemented")
    }

    override fun returnToInstructors() {
        TODO("Not yet implemented")
    }

    override fun goToSignUpPage() {
        navController?.navigate(R.id.signUpFragment)
    }

    override fun goToInstructorsList() {
        navController?.navigate(R.id.instructorsFragment)
    }

}
