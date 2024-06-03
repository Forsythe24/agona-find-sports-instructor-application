package com.solopov.feature_event_calendar_impl.data.mappers

import com.solopov.common.data.db.model.EventLocal
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem
import javax.inject.Inject

class EventMappers @Inject constructor() {
    fun mapEventLocalToEvent(eventLocal: EventLocal): Event {
        return with(eventLocal) {
            Event(
                id,
                name,
                personName,
                date,
                startTime,
                endTime,
                place
            )
        }
    }

    fun mapEventItemToEvent(eventItem: EventItem): Event {
        return with(eventItem) {
            Event(
                id,
                name,
                personName,
                date,
                startTime,
                endTime,
                place
            )
        }
    }

    fun mapEventToEventItem(event: Event): EventItem {
        return with(event) {
            EventItem(
                id,
                name,
                personName,
                date,
                startTime,
                endTime,
                place
            )
        }
    }

    fun mapEventToEventLocal(event: Event): EventLocal {
        return with(event) {
            EventLocal(
                id,
                name,
                personName,
                date,
                startTime,
                endTime,
                place
            )
        }
    }
}
