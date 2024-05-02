package com.solopov.feature_chat_impl

import com.solopov.common.model.ChatCommon
import com.solopov.common.model.UserCommon

interface ChatRouter {

    fun openChat(chat: ChatCommon)

}
