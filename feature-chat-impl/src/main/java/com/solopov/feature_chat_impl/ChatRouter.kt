package com.solopov.feature_chat_impl

import com.solopov.common.model.ChatCommon

interface ChatRouter {

    fun openChat(chat: ChatCommon)
    fun openUserProfile(chat: ChatCommon)

}
