package com.solopov.app.di.main

import com.solopov.app.di.deps.ComponentDependencies
import com.solopov.app.navigation.Navigator
import com.solopov.common.data.storage.UserDataStore

interface MainDependencies : ComponentDependencies {

    fun navigator(): Navigator
    fun userDataStore(): UserDataStore
}
