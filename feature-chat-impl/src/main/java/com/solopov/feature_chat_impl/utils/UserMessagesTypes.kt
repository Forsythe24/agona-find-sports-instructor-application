package com.solopov.feature_chat_impl.utils

enum class UserMessagesTypes(val number: Int) {
    SENT_MESSAGE(number = 1),
    RECEIVED_MESSAGE(number = 2),
    DATA_MESSAGE(number = 3),
}
