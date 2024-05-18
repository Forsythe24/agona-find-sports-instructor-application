package com.solopov.feature_event_calendar_impl.data.mappers

import com.solopov.common.data.remote.model.UserRemote
import com.solopov.feature_event_calendar_api.domain.model.User
import com.solopov.feature_event_calendar_impl.presentation.model.EventCalendar
import javax.inject.Inject

class UserMappers @Inject constructor() {

    fun mapUserRemoteToUser(userRemote: UserRemote): User {
        return with(userRemote) {
            User(
                userRemote.id,
                name,
                age,
                gender,
                sportName,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapUserToUserRemote(user: User): UserRemote {
        return with(user) {
            UserRemote(
                user.id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapUserToEventCalendar(user: User): EventCalendar {
        return with(user) {
            EventCalendar(
                id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

    fun mapEventCalendarToUser(eventCalendar: EventCalendar): User {
        return with(eventCalendar) {
            User(
                id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
                isInstructor
            )
        }
    }

}
