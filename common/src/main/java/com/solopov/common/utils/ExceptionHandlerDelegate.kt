package com.solopov.common.utils

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor(
    private val resManager: ResourceManager
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is FirebaseAuthWeakPasswordException -> AuthenticationException.SignUpException(resManager.getString(R.string.weak_password_exception))

//            is FirebaseException -> {
//                when (ex) {
//                    is FirebaseAuthWeakPasswordException -> AuthenticationException.SignUpException(resManager.getString(R.string.weak_password_exception))
//                    else -> {
//                        Exception()
//                    }
//                }
//            }

            else -> {
                println("haha")
                Exception(ex.message)
            }
        }

    }
}
