package com.solopov.feature_authentication_impl

interface AuthRouter {

    fun goBack()
    fun goToSignUp()
    fun goToInstructorsList()
    fun goFromLogInToUserProfile()
    fun goFromSignUpToInstructors()

    fun goToPasswordRecovery()
}
