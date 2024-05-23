package com.solopov.common.data.remote.model

data class MessageRemote(
    var id: Long?,
    var chatId: String,
    var text: String,
    val senderId: String,
    val date: String,
)
