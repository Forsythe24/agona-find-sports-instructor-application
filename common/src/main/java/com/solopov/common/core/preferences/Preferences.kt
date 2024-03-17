package com.solopov.common.core.preferences

interface Preferences {

    fun saveAccessToken(token: String)

    fun getAccessToken(): String
}
