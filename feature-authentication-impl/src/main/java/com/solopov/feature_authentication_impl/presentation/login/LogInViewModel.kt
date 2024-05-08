package com.solopov.feature_authentication_impl.presentation.login

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
): BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val _authenticationResultFlow = MutableStateFlow(false)
    val authenticationResultFlow: StateFlow<Boolean>
        get() = _authenticationResultFlow

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    fun signIn(
        email: String?,
        password: String?,
    ){
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.signInUser(
                    email,
                    password,
                )
            }.onSuccess {
                _authenticationResultFlow.value = it
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
