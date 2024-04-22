package com.solopov.common.utils

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import com.solopov.common.data.firebase.exceptions.UserDoesNotExistException
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor(
    private val resManager: ResourceManager
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {

            is FirebaseException -> {
                when (ex) {
                    is FirebaseAuthWeakPasswordException -> AuthenticationException.WeakPasswordException(resManager.getString(R.string.weak_password_exception))

                    is FirebaseAuthInvalidUserException -> AuthenticationException.NoSuchUserException(resManager.getString(R.string.invalid_user))

                    is FirebaseAuthInvalidCredentialsException -> AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))

                    is FirebaseAuthUserCollisionException -> AuthenticationException.EmailAlreadyInUseException(resManager.getString(R.string.email_in_use))

                    else -> ex
                }
            }
            else -> ex
        }

    }
}
