package com.solopov.feature_user_profile_impl.presentation.instruct

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_user_profile_api.domain.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructApplicationViewModel @Inject constructor(
    private val interactor: UserProfileInteractor,
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit,
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                interactor.updateUser(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
                _progressBarFlow.value = false
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
                _progressBarFlow.value = false
            }
        }
    }

    fun goBack() {
        router.goBack()
    }

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }
}
