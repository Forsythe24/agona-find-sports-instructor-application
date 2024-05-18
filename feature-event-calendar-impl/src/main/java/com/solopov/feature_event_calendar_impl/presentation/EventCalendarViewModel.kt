package com.solopov.feature_event_calendar_impl.presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarInteractor
import com.solopov.feature_event_calendar_api.domain.model.User
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import com.solopov.feature_event_calendar_impl.data.mappers.UserMappers
import com.solopov.feature_event_calendar_impl.presentation.model.EventCalendar
import com.solopov.feature_event_calendar_impl.presentation.model.RatingUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventCalendarViewModel(
    private val interactor: EventCalendarInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val userMappers: UserMappers,
    private val eventCalendarRouter: EventCalendarRouter,
) : BaseViewModel() {

    private val _eventCalendarFlow = MutableStateFlow<EventCalendar?>(null)
    val eventCalendarFlow: StateFlow<EventCalendar?>
        get() = _eventCalendarFlow

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

    val errorsChannel = Channel<Throwable>()

    fun setEventCalendarByUid(uid: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getUserByUid(uid)
            }.onSuccess {
                _eventCalendarFlow.value = userMappers.mapUserToEventCalendar(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setChat(chat: ChatCommon) {
        _chatFlow.value = chat
    }

    fun setCurrentUser(userId: String, onUserSetCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _currentUserFlow.value = it
                onUserSetCallback(it.id == userId)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setCurrentEventCalendar() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _eventCalendarFlow.value = userMappers.mapUserToEventCalendar(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }


    fun updateUser(
        eventCalendar: EventCalendar
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUser(userMappers.mapEventCalendarToUser(eventCalendar))
            }.onSuccess {
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    fun setEventCalendar(user: EventCalendar) {
        _eventCalendarFlow.value = user
    }

    fun set(user: EventCalendar) {
        _eventCalendarFlow.value = user
    }

    fun updateProfileImage(imageUri: String) {
        _eventCalendarFlow.value?.let {
            it.photo = imageUri
            updateUser(it)
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.uploadProfileImage(imageUri.toString())
            }.onSuccess {
                updateProfileImage(it)
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
