package com.solopov.common.data.remote.model

data class UserSignUpRemote(
    var email: String,
    var password: String,
    var name: String,
    var age: Int,
    var gender: String,
)
