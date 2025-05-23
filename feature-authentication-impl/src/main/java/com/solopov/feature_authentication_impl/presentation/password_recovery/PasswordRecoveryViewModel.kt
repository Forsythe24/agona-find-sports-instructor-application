package com.solopov.feature_authentication_impl.presentation.password_recovery

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.usecase.SendNewPasswordUseCase
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordRecoveryViewModel @Inject constructor(
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
    private val sendNewPasswordUseCase: SendNewPasswordUseCase
) : BaseViewModel() {

    fun sendNewPassword(email: String, onPasswordSentCallback: (String) -> Unit) {
        viewModelScope.launch {
            runCatching {
                sendNewPasswordUseCase(email)
            }.onSuccess {
                onPasswordSentCallback(email)
            }.onFailure {
                showMessage(
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
}
