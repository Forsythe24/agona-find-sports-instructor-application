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
import kotlinx.coroutines.flow.asStateFlow
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

    private val _eventListState= MutableStateFlow<List<EventItem>?>(null)
    val eventListState: StateFlow<List<EventItem>?> = _eventListState.asStateFlow()

    private val _possiblePartnersNameListState = MutableStateFlow<List<String>?>(null)
    val possiblePartnersNameListState: StateFlow<List<String>?> = _possiblePartnersNameListState.asStateFlow()

    private val _currentUserIdState = MutableStateFlow<String?>(null)
    val currentUserIdState: StateFlow<String?> = _currentUserIdState.asStateFlow()

    private val _currentEventState = MutableStateFlow<EventItem?>(null)
    val currentEventState: StateFlow<EventItem?> = _currentEventState.asStateFlow()

    fun getAllEventsByDate(date: Date) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                getAllEventsByDateUseCase(date)
            }.onSuccess {
                _eventListState.value = it?.map(eventMappers::mapEventToEventItem)?.sortByStartTime()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                setLoadingState(false)
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
                _possiblePartnersNameListState.value = it
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
        _currentEventState.value = eventItem
    }

    fun saveEvent(eventItem: EventItem, onEventSavedCallback: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                addEventUseCase(eventMappers.mapEventItemToEvent(eventItem))
            }.onSuccess {
                onEventSavedCallback()
                _currentEventState.value = null
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
                _currentUserIdState.value = it
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
