package com.solopov.feature_event_calendar_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.remote.dao.ChatRemoteDao
import com.solopov.common.data.remote.dao.UserRemoteDao
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import java.util.Date
import javax.inject.Inject

class EventCalendarRepositoryImpl @Inject constructor(
    private val eventMappers: EventMappers,
    private val chatRemoteDao: ChatRemoteDao,
    private val userRemoteDao: UserRemoteDao,
    private val db: AppDatabase,
) : EventCalendarRepository {
    override suspend fun addEvent(event: Event) {
        return db.eventDao().addEvent(eventMappers.mapEventToEventLocal(event))
    }

    override suspend fun getAllEventsByDate(date: Date): List<Event>? {
        return db.eventDao().getAllEventsByDate(date.time)?.map(eventMappers::mapEventLocalToEvent)
    }

    override suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String> {
        return chatRemoteDao.getAllReceiversByUserId(userId).map { id ->
            userRemoteDao.getUserByUid(id).name
        }
    }

    override suspend fun deleteEventById(id: Long) {
        return db.eventDao().deleteEventById(id)
    }

    override suspend fun getCurrentUserId(): String {
        return userRemoteDao.getCurrentUserId()
    }

    override suspend fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        return db.eventDao().deleteAllEventsThreeOrMoreDaysAgo(date.time)
    }
}

