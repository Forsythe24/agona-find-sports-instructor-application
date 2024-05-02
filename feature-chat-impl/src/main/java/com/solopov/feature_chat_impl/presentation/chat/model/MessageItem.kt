package com.solopov.feature_chat_impl.presentation.chat.model

import java.util.Date

data class MessageItem (
    val id: String?,
    val text: String,
    val senderId: String,
    val date: String,
)
