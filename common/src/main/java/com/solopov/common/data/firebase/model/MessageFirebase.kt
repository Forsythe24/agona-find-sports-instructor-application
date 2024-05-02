package com.solopov.common.data.firebase.model

import java.util.Date

data class MessageFirebase(
    var id: String,
    var text: String,
    val senderId: String,
    val date: String,
)
