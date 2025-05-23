package com.solopov.common.base.model

data class Message(
	val text: String,
	val action: MessageAction? = null
)
