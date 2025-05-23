package com.solopov.feature_event_calendar_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_event_calendar_api.domain.usecase.AddEventUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteAllRecentEventsUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteEventUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllEventsByDateUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllPossiblePartnersNamesUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetCurrentUserIdUseCase
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EventCalendarViewModel(
    private val eventMappers: EventMappers,
    private val resourceManager: ResourceManager,
    private val getAllEventsByDateUseCase: GetAllEventsByDateUseCase,
    private val deleteAllRecentEventsUseCase: DeleteAllRecentEventsUseCase,
    private val getAllPossiblePartnersNamesUseCase: GetAllPossiblePartnersNamesUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
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

    fun getAllEventsByDate(date: Date) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                getAllEventsByDateUseCase(date)
            }.onSuccess {
                _eventListFlow.value = it?.map(eventMappers::mapEventToEventItem)?.sortByStartTime()
                _progressBarFlow.value = false
            }.onFailure {
                _progressBarFlow.value = false
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        viewModelScope.launch {
            runCatching {
                deleteAllRecentEventsUseCase(date)
            }.onSuccess {
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun getAllPossiblePartnersNamesByUserId(userId: String) {
        viewModelScope.launch {
            runCatching {
                getAllPossiblePartnersNamesUseCase(userId)
            }.onSuccess {
                _possiblePartnersNameListFlow.value = it
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun deleteEvent(event: EventItem, onEventDeletedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                deleteEventUseCase(event.id)
            }.onSuccess {
                onEventDeletedCallback()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun setCurrentEvent(eventItem: EventItem) {
        _currentEventFlow.value = eventItem
    }

    fun saveEvent(eventItem: EventItem, onEventSavedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                addEventUseCase(eventMappers.mapEventItemToEvent(eventItem))
            }.onSuccess {
                onEventSavedCallback()
                _currentEventFlow.value = null
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            runCatching {
                getCurrentUserIdUseCase()
            }.onSuccess {
                _currentUserIdFlow.value = it
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    private fun List<EventItem>.sortByStartTime(): List<EventItem> {
        return this.sortedBy { event ->
            event.startTime
        }
    }
}
