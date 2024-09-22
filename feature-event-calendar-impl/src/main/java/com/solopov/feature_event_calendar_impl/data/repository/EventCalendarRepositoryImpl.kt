package com.solopov.feature_event_calendar_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.exceptions.ChatDataRetrievingException
import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.exceptions.UserException
import com.solopov.common.data.network.utils.HttpValues
import com.solopov.common.data.network.utils.handleApiErrors
import com.solopov.common.data.network.utils.makeSafeApiCall
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import java.net.HttpURLConnection
import java.util.Date
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class EventCalendarRepositoryImpl @Inject constructor(
    private val eventMappers: EventMappers,
    private val db: AppDatabase,
    private val chatApiService: ChatApiService,
    private val userApiService: UserApiService,
) : EventCalendarRepository {
    override suspend fun addEvent(event: Event) {
        return db.eventDao().addEvent(eventMappers.mapEventToEventLocal(event))
    }

    override suspend fun getAllEventsByDate(date: Date): List<Event>? {
        return db.eventDao().getAllEventsByDate(date.time)?.map(eventMappers::mapEventLocalToEvent)
    }

    override suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String> {
        val response = chatApiService.getAllChatsByUserId()

        val receiversIds = when (response.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> throw HttpException.UnauthorizedException("Failed to fetch user ($userId) chat list due to lack of authority")
            in HttpValues.serverErrorRange -> throw HttpException.ServerFailedException("The server encountered an unexpected condition")
            in HttpValues.clientErrorRange -> throw HttpException.ClientException()
            HttpsURLConnection.HTTP_OK -> {
                if (response.body() == null) {
                    listOf()
                } else {
                    response.body()!!.map { chat ->
                        val receiverId = if (chat.id.startsWith(userId)) {
                            chat.id.removePrefix(userId)
                        } else {
                            chat.id.removeSuffix(userId)
                        }

                        receiverId
                    }
                }
            }

            else -> throw ChatDataRetrievingException("Failed to retrieve chat data")
        }


        return receiversIds.map { id ->
            makeSafeApiCall {
                userApiService.getUser(id)
            }.handleApiErrors(
                hashMapOf(
                    HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                        "Failed to download possible partner's data by uid due to lack of authority",
                    ),
                    HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("User ($id) was not found")
                )
            ).name
        }
    }

    override suspend fun deleteEventById(id: Long) {
        return db.eventDao().deleteEventById(id)
    }

    override suspend fun getCurrentUserId(): String {
        return makeSafeApiCall {
            userApiService.getCurrentUser()
        }.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                    "Failed to fetch current user's id due to their lack of authority",
                ),
                HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("Current user was not found")
            )
        ).id
    }

    override suspend fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        return db.eventDao().deleteAllEventsThreeOrMoreDaysAgo(date.time)
    }
}

