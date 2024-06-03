package com.solopov.feature_chat_impl

import com.solopov.common.model.ChatCommon

interface ChatRouter {

    fun goBackToChats()
    fun goFromChatsToChat(chat: ChatCommon)
    fun openUserProfile(userId: String)
    fun goFromChatToEventCalendar(partnerName: String)
}
