package com.solopov.feature_chat_impl.presentation.chat_list.model

data class ChatItem(
    val userId: String,
    val name: String,
    val photo: String?,
    var lastMessageDate: String?,
    var lastMessageText: String?,
    var userFriendlyLastMessageDate: String?
)
