package com.solopov.feature_authentication_impl.presentation.password_recovery

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordRecoveryViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val router: AuthRouter,
) : BaseViewModel() {

    val errorsChannel = Channel<Throwable>()

    private val _state = MutableStateFlow(Boolean)
    val state = _state.asStateFlow()

    fun sendNewPassword(email: String, onPasswordSentCallback: (String) -> Unit) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.sendNewPassword(email)
            }.onSuccess {
                onPasswordSentCallback(email)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun goBack() {
        router.goBack()
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
