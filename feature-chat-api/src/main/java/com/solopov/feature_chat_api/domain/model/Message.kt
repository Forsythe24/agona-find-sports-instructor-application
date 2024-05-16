package com.solopov.feature_chat_api.domain.model

import java.util.Date

data class Message (
    val id: Long?,
    val chatId: String,
    val text: String,
    val senderId: String,
    val date: String,
)
