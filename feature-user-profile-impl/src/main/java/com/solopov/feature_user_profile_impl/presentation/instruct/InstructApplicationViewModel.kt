package com.solopov.feature_user_profile_impl.presentation.instruct

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructApplicationViewModel @Inject constructor(
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
    private val resourceManager: ResourceManager,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : BaseViewModel() {

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit,
    ) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                updateUserInfoUseCase(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                setLoadingState(false)
            }
        }
    }

    fun goBack() {
        router.goBack()
    }
}
