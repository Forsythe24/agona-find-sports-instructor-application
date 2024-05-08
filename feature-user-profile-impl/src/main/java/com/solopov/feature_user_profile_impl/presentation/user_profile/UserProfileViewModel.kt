package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.common.base.BaseViewModel
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.data.mappers.RatingMappers
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.RatingUi
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val interactor: UserProfileInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val userMappers: UserMappers,
    private val ratingMappers: RatingMappers
): BaseViewModel() {

    private val _userProfileFlow = MutableStateFlow<UserProfile?>(null)
    val userProfileFlow: StateFlow<UserProfile?>
        get() = _userProfileFlow

    private val _currentUserFlow = MutableStateFlow<User?>(null)
    val currentUserFlow: StateFlow<User?>
        get() = _currentUserFlow

    private val _ratingsFlow = MutableStateFlow<List<RatingUi>?>(null)
    val ratingsFlow: StateFlow<List<RatingUi>?>
        get() = _ratingsFlow

    val errorsChannel = Channel<Throwable>()

    fun getUserByUid(uid: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getUserByUid(uid)
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setCurrentUser() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _currentUserFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setCurrentUserProfile() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun addRating(ratingUi: RatingUi) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.addRating(ratingMappers.mapRatingUiToRating(ratingUi))
            }.onSuccess {
                getAllInstructorRatingsById(ratingUi.instructorId)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun updateUserRating(
        userProfile: UserProfile
    ) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUserRating(userMappers.mapUserProfileToUserForRating(userProfile))
            }.onSuccess {
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    private fun getAllInstructorRatingsById(
        instructorId: String
    ) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getAllInstructorRatingsById(instructorId)
            }.onSuccess {
                _ratingsFlow.value = it?.map(ratingMappers::mapRatingToRatingUi) ?: listOf()
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setUserProfile(user: UserCommon) {
        _userProfileFlow.value = userMappers.mapUserCommonToUserProfile(user)
    }

    fun set(user: UserProfile) {
        _userProfileFlow.value = user
    }

    fun updateProfileImage(imageUri: String) {
        _userProfileFlow.value?.let {
            it.photo = imageUri
            updateUserRating(it)
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.uploadProfileImage(imageUri.toString())
            }.onSuccess {
                updateProfileImage(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
