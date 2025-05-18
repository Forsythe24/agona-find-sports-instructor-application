package com.solopov.feature_event_calendar_api.domain.usecase

interface GetAllPossiblePartnersNamesUseCase {
    suspend operator fun invoke(userId: String): List<String>?
}
