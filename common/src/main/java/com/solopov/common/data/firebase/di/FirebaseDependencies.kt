package com.solopov.common.data.firebase.di

import android.content.Context
import com.solopov.common.core.resources.ResourceManager

interface FirebaseDependencies {
    fun context(): Context
    fun resourceManager(): ResourceManager
}
