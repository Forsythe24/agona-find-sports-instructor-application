package com.solopov.feature_chat_api.domain.model

data class Chat(
    val userId: String,
    val name: String,
    val photo: String?,
    var lastMessageDate: String?,
    var lastMessageText: String?,
)
