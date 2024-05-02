package com.solopov.common.model

import java.io.Serializable

data class ChatCommon (
    val userId: String,
    val name: String,
    val photo: String?,
): Serializable
