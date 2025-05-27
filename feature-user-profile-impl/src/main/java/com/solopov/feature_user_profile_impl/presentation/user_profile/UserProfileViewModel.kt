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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _userProfileState = MutableStateFlow<UserProfile?>(null)
    val userProfileState: StateFlow<UserProfile?> = _userProfileState.asStateFlow()

    private val _currentUserState = MutableStateFlow<User?>(null)
    val currentUserState: StateFlow<User?> = _currentUserState.asStateFlow()

    private val _ratingsState = MutableStateFlow<List<RatingUi>?>(null)
    val ratingsState: StateFlow<List<RatingUi>?> = _ratingsState.asStateFlow()

    private val _chatState = MutableStateFlow<ChatCommon?>(null)
    val chatState: StateFlow<ChatCommon?> = _chatState.asStateFlow()

    fun setUserProfileById(uid: String) {
        viewModelScope.launch {
            runCatching {
                loadUserInfoUseCase(uid)
            }.onSuccess {
                _userProfileState.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun setChat(chat: ChatCommon) {
        _chatState.value = chat
    }

    fun setCurrentUser(userId: String, onUserSetCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _currentUserState.value = it
                onUserSetCallback(it.id == userId)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
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
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun setCurrentUserProfile() {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _userProfileState.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
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
                showMessage(it.getMessage(resourceManager))
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
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    private fun updateUserProfile(
        userProfile: UserProfile,
    ) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                updateUserInfoUseCase(userMappers.mapUserProfileToUser(userProfile))
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                setLoadingState(false)
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
                _ratingsState.value = it?.map(ratingMappers::mapRatingToRatingUi) ?: listOf()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    private fun setUserProfile(user: UserProfile) {
        _userProfileState.value = user
    }

    fun set(user: UserProfile) {
        _userProfileState.value = user
    }

    private fun updateProfileImage(imageUri: String) {
        _userProfileState.value?.let {
            it.photo = imageUri
            updateUserProfile(it)
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                uploadProfileImageUseCase(imageUri.toString())
            }.onSuccess {
                updateProfileImage(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                setLoadingState(false)
            }
        }
    }

    fun updateRating() {
        val allRatings = ratingsState.value

        if (allRatings != null) {
            val ratingsSum = allRatings.map { ratingUi ->
                ratingUi.rating
            }.sum()

            val newRating = ratingsSum / if (allRatings.isEmpty()) 1 else allRatings.size

            userProfileState.value?.let {
                val newUserProfile =
                    it.copy(rating = newRating, numberOfRatings = allRatings.size)

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
}
