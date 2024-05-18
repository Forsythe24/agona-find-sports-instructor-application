package com.solopov.feature_authentication_impl.presentation.password_recovery

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordRecoveryViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
): BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val _state = MutableStateFlow(FirstState())
    val state = _state.asStateFlow()

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    fun sendNewPassword(email: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.sendNewPassword(email)
            }.onSuccess {

            }.onFailure {
                throw it
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }

    class FirstState
}
