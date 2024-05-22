package com.solopov.feature_chat_impl

import com.solopov.common.model.ChatCommon

interface ChatRouter {

    fun goBack()
    fun openChat(chat: ChatCommon)
    fun openUserProfile(userId: String)
    fun goToEventCalendar(partnerName: String)
}
