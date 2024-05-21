package com.solopov.feature_event_calendar_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarInteractor
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EventCalendarViewModel(
    private val interactor: EventCalendarInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val eventMappers: EventMappers,
    private val eventCalendarRouter: EventCalendarRouter,
) : BaseViewModel() {

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    private val _eventListFlow = MutableStateFlow<List<EventItem>?>(null)
    val eventListFlow: StateFlow<List<EventItem>?>
        get() = _eventListFlow

    private val _possiblePartnersNameListFlow = MutableStateFlow<List<String>?>(null)
    val possiblePartnersNameListFlow: StateFlow<List<String>?>
        get() = _possiblePartnersNameListFlow

    private val _currentUserIdFlow = MutableStateFlow<String?>(null)
    val currentUserIdFlow: StateFlow<String?>
        get() = _currentUserIdFlow

    private val _currentEventFlow = MutableStateFlow<EventItem?>(null)
    val currentEventFlow: StateFlow<EventItem?>
        get() = _currentEventFlow

    val errorsChannel = Channel<Throwable>()

    fun getAllEventsByDate(date: Date) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getAllEventsByDate(date)
            }.onSuccess {
                _eventListFlow.value = it?.map(eventMappers::mapEventToEventItem)?.sortByStartTime()
                _progressBarFlow.value = false
            }.onFailure {
                _progressBarFlow.value = false
                errorsChannel.send(it)
            }
        }
    }

    fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.deleteAllEventsThreeOrMoreDaysAgo(date)
            }.onSuccess {
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun getAllPossiblePartnersNamesByUserId(userId: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getAllPossiblePartnersNamesByUserId(userId)
            }.onSuccess {
                _possiblePartnersNameListFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun deleteEvent(event: EventItem, onEventDeletedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.deleteEventById(event.id)
            }.onSuccess {
                onEventDeletedCallback()
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setCurrentEvent(eventItem: EventItem) {
        _currentEventFlow.value = eventItem
    }

    fun saveEvent(eventItem: EventItem, onEventCreatedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.addEvent(eventMappers.mapEventItemToEvent(eventItem))
            }.onSuccess {
                onEventCreatedCallback()
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUserId()
            }.onSuccess {
                _currentUserIdFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }


    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }

    private fun List<EventItem>.sortByStartTime(): List<EventItem> {
        return this.sortedBy { event ->
            event.startTime
        }
    }
}
