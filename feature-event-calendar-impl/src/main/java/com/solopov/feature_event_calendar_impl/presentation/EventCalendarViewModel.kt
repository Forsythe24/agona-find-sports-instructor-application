package com.solopov.feature_event_calendar_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_event_calendar_api.domain.EventCalendarInteractor
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date

class EventCalendarViewModel(
    private val interactor: EventCalendarInteractor,
    private val eventMappers: EventMappers,
    private val resourceManager: ResourceManager,
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

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    fun getAllEventsByDate(date: Date) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                interactor.getAllEventsByDate(date)
            }.onSuccess {
                _eventListFlow.value = it?.map(eventMappers::mapEventToEventItem)?.sortByStartTime()
                _progressBarFlow.value = false
            }.onFailure {
                _progressBarFlow.value = false
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        viewModelScope.launch {
            runCatching {
                interactor.deleteAllEventsThreeOrMoreDaysAgo(date)
            }.onSuccess {
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun getAllPossiblePartnersNamesByUserId(userId: String) {
        viewModelScope.launch {
            runCatching {
                interactor.getAllPossiblePartnersNamesByUserId(userId)
            }.onSuccess {
                _possiblePartnersNameListFlow.value = it
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun deleteEvent(event: EventItem, onEventDeletedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                interactor.deleteEventById(event.id)
            }.onSuccess {
                onEventDeletedCallback()
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun setCurrentEvent(eventItem: EventItem) {
        _currentEventFlow.value = eventItem
    }

    fun saveEvent(eventItem: EventItem, onEventSavedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                interactor.addEvent(eventMappers.mapEventItemToEvent(eventItem))
            }.onSuccess {
                onEventSavedCallback()
                _currentEventFlow.value = null
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            runCatching {
                interactor.getCurrentUserId()
            }.onSuccess {
                _currentUserIdFlow.value = it
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
            }
        }
    }


    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }

    private fun List<EventItem>.sortByStartTime(): List<EventItem> {
        return this.sortedBy { event ->
            event.startTime
        }
    }
}
