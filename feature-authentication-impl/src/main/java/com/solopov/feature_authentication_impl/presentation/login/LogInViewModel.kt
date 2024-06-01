package com.solopov.feature_authentication_impl.presentation.login

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.UserDataValidator
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
    private val router: AuthRouter,
    private val validator: UserDataValidator,
) : BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val _authenticationResultFlow = MutableStateFlow(false)
    val authenticationResultFlow: StateFlow<Boolean>
        get() = _authenticationResultFlow

    fun signIn(
        email: String?,
        password: String?,
    ) {
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

    fun validateEmail(email: String): String? {
        return validator.validateEmail(email)
    }

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
