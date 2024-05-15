package com.solopov.feature_chat_impl.presentation.chat.model


data class MessageItem (
    val id: Long?,
    val chatId: String,
    val text: String,
    val senderId: String,
    val date: String,
)
