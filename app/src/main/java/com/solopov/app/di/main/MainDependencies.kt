package com.solopov.app.di.main

import com.solopov.app.di.deps.ComponentDependencies
import com.solopov.app.navigation.Navigator

interface MainDependencies : ComponentDependencies {

    fun navigator(): Navigator
}
