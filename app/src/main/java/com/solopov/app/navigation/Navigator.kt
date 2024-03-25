package com.solopov.app.navigation

import androidx.navigation.NavController
import com.solopov.feature_instructor_impl.InstructorsRouter

class Navigator : InstructorsRouter {

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

}
