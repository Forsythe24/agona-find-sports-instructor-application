package com.solopov.feature_authentication_impl.presentation.password_recovery

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordRecoveryViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    fun sendNewPassword(email: String, onPasswordSentCallback: (String) -> Unit) {
        viewModelScope.launch {
            runCatching {
                interactor.sendNewPassword(email)
            }.onSuccess {
                onPasswordSentCallback(email)
            }.onFailure {
                _errorMessageChannel.send(
                    when (it) {
                        is ApiError.NotFoundException -> resourceManager.getString(R.string.no_such_email_message)
                        else -> it.getMessage(resourceManager)
                    }
                )
            }
        }
    }

    fun goBack() {
        router.goBack()
    }

    fun validateEmail(text: String): String? {
        return validator.validateEmail(text)
    }

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }
}
