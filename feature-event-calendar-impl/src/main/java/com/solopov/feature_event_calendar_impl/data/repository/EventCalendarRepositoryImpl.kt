package com.solopov.feature_event_calendar_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.makeSafeApiCall
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import java.util.Date
import javax.inject.Inject

class EventCalendarRepositoryImpl @Inject constructor(
    private val eventMappers: EventMappers,
    private val db: AppDatabase,
    private val chatApiService: ChatApiService,
    private val userApiService: UserApiService,
    private val networkStateProvider: NetworkStateProvider,
) : EventCalendarRepository {
    override suspend fun addEvent(event: Event) {
        return db.eventDao().addEvent(eventMappers.mapEventToEventLocal(event))
    }

    override suspend fun getAllEventsByDate(date: Date): List<Event>? {
        return db.eventDao().getAllEventsByDate(date.time)?.map(eventMappers::mapEventLocalToEvent)
    }

    override suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String> {
        val chats = makeSafeApiCall(networkStateProvider) {
            chatApiService.getAllChatsByUserId()
        } ?: listOf()

        val receiversIds = chats.map { chat ->
            val receiverId = if (chat.id.startsWith(userId)) {
                chat.id.removePrefix(userId)
            } else {
                chat.id.removeSuffix(userId)
            }

            receiverId
        }

        return receiversIds.map { id ->
            makeSafeApiCall(networkStateProvider) {
                userApiService.getUser(id)
            }.name
        }
    }

    override suspend fun deleteEventById(id: Long) {
        return db.eventDao().deleteEventById(id)
    }

    override suspend fun getCurrentUserId(): String {
        return makeSafeApiCall(networkStateProvider) {
            userApiService.getCurrentUser()
        }.id
    }

    override suspend fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        return db.eventDao().deleteAllEventsThreeOrMoreDaysAgo(date.time)
    }
}

