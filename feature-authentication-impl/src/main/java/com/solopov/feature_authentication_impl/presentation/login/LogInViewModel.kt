package com.solopov.feature_authentication_impl.presentation.login

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val resManager: ResourceManager,
    private val router: AuthRouter,
): BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val _authenticationResultFlow = MutableStateFlow(false)
    val authenticationResultFlow: StateFlow<Boolean>
        get() = _authenticationResultFlow

    fun signIn(
        email: String?,
        password: String?,
    ){
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.signInUser(
                    email,
                    password,
                )
            }.onSuccess {
                _authenticationResultFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

//    fun consumeErrors() {
//        viewModelScope.launch {
//            errorsChannel.consumeEach { error ->
//                val errorMessage = error.message ?: resManager.getString(R.string.unknown_error)
//
//                when (error) {
//                    is AuthException.NoSuchEmailException, is AuthException.InvalidEmailException -> {
//                        println(errorMessage)
////                        emailTextInput.helperText = error.message
//                    }
//
//                    is AuthException.NoEmptyPasswordException -> {
//                        println(errorMessage)
////                        passwordTextInput.helperText = error.message
//                    }
//
//                    is AuthException.WrongEmailOrPasswordException -> {
//                        println(errorMessage)
//                        showAlert(resManager.getString(R.string.authentication_error))
//                    }
//
//                    else -> println(errorMessage)
//                }
//            }
//        }
//    }

    fun goFromLogInToUserProfile() {
        router.goFromLogInToUserProfile()
    }

    fun goToSignUp() {
        router.goToSignUp()
    }

    fun goToPasswordRecovery() {
        router.goToPasswordRecovery()
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
