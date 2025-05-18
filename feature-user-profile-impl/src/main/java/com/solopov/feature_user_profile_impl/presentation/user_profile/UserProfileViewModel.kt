package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.common.model.ChatCommon
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_api.domain.usecase.AddRatingUseCase
import com.solopov.feature_user_profile_api.domain.usecase.DeleteProfileUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetAllInstructorRatingsUseCase
import com.solopov.feature_user_profile_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LoadUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.LogOutUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UploadProfileImageUseCase
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.RatingMappers
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.RatingUi
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userMappers: UserMappers,
    private val ratingMappers: RatingMappers,
    private val router: UserProfileRouter,
    private val resourceManager: ResourceManager,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val addRatingUseCase: AddRatingUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getAllInstructorRatingsUseCase: GetAllInstructorRatingsUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase
) : BaseViewModel() {

    private val _userProfileFlow = MutableStateFlow<UserProfile?>(null)
    val userProfileFlow: StateFlow<UserProfile?>
        get() = _userProfileFlow

    private val _currentUserFlow = MutableStateFlow<User?>(null)
    val currentUserFlow: StateFlow<User?>
        get() = _currentUserFlow

    private val _ratingsFlow = MutableStateFlow<List<RatingUi>?>(null)
    val ratingsFlow: StateFlow<List<RatingUi>?>
        get() = _ratingsFlow

    private val _chatFlow = MutableStateFlow<ChatCommon?>(null)
    val chatFlow: StateFlow<ChatCommon?>
        get() = _chatFlow

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    fun setUserProfileById(uid: String) {
        viewModelScope.launch {
            runCatching {
                loadUserInfoUseCase(uid)
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun setChat(chat: ChatCommon) {
        _chatFlow.value = chat
    }

    fun setCurrentUser(userId: String, onUserSetCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _currentUserFlow.value = it
                onUserSetCallback(it.id == userId)
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun deleteProfile(onProfileDeletedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                deleteProfileUseCase()
            }.onSuccess {
                onProfileDeletedCallback()
                goToLogInScreen()
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun setCurrentUserProfile() {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun logOut(onLoggedOutCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                logOutUseCase()
            }.onSuccess {
                onLoggedOutCallback()
                goToLogInScreen()
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun addRating(ratingUi: RatingUi) {
        viewModelScope.launch {
            runCatching {
                addRatingUseCase(ratingMappers.mapRatingUiToRating(ratingUi))
            }.onSuccess {
                getAllInstructorRatingsById(ratingUi.instructorId)
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    private fun updateUserProfile(
        userProfile: UserProfile,
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                updateUserInfoUseCase(userMappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                _progressBarFlow.value = false
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
                _progressBarFlow.value = false
            }
        }
    }

    private fun getAllInstructorRatingsById(
        instructorId: String,
    ) {
        viewModelScope.launch {
            runCatching {
                getAllInstructorRatingsUseCase(instructorId)
            }.onSuccess {
                _ratingsFlow.value = it?.map(ratingMappers::mapRatingToRatingUi) ?: listOf()
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    private fun setUserProfile(user: UserProfile) {
        _userProfileFlow.value = user
    }

    fun set(user: UserProfile) {
        _userProfileFlow.value = user
    }

    private fun updateProfileImage(imageUri: String) {
        _userProfileFlow.value?.let {
            it.photo = imageUri
            updateUserProfile(it)
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                uploadProfileImageUseCase(imageUri.toString())
            }.onSuccess {
                updateProfileImage(it)
                _progressBarFlow.value = false
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
                _progressBarFlow.value = false
            }
        }
    }

    fun updateRating() {

        val allRatings = ratingsFlow.value

        if (allRatings != null) {
            val ratingsSum = allRatings.map { ratingUi ->
                ratingUi.rating
            }.sum()

            val newNumberOfRatings = allRatings.size
            val newRating = ratingsSum / if (allRatings.isEmpty()) 1 else newNumberOfRatings

            userProfileFlow.value?.let {
                val newUserProfile =
                    it.copy(rating = newRating, numberOfRatings = newNumberOfRatings)

                updateUserProfile(newUserProfile)
                setUserProfile(newUserProfile)
            }
        }
    }

    fun goBack() {
        router.goBack()
    }

    fun goToEditingProfile(userProfile: UserProfile) {
        router.goToEditingProfile(userProfile)
    }

    fun goToInstructApplication(userProfile: UserProfile) {
        router.goToInstructApplication(userProfile)
    }

    fun openChat(chat: ChatCommon) {
        router.goFromUserProfileToChat(chat)
    }

    private fun goToLogInScreen() {
        router.goFromUserProfileToLogInScreen()
    }

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }
}
