package com.solopov.feature_user_profile_impl.presentation.instruct

import androidx.lifecycle.viewModelScope
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructApplicationViewModel @Inject constructor(
    private val interactor: UserProfileInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
    private val resManager: ResourceManager
) : BaseViewModel() {

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    val errorsChannel = Channel<Throwable>()

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUser(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    fun validateExperienceField(experience: String): String? = if (experience.isEmpty()) {
        resManager.getString(R.string.experience_field_empty_helper_text)
    } else {
        null
    }


    fun goBack() {
        router.goBack()
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
