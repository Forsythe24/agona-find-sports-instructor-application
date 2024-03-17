package com.solopov.common.di.modules

import android.app.NotificationManager
import android.content.Context
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.core.resources.ResourceManagerImpl
import com.solopov.common.core.preferences.Preferences
import com.solopov.common.data.storage.PreferencesImpl
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.notification.NotificationManagerWrapper
import com.solopov.common.notification.NotificationManagerWrapperImpl
import com.solopov.common.utils.DateFormatter
import dagger.Module
import dagger.Provides

@Module
class CommonModule {

    @Provides
    @ApplicationScope
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }

    @Provides
    @ApplicationScope
    fun provideAppProperties(context: Context): AppProperties {
        return AppProperties(context)
    }

    @Provides
    @ApplicationScope
    fun providePreferences(context: Context): Preferences {
        return PreferencesImpl(context)
    }

    @Provides
    fun provideDateFormatter(): DateFormatter {
        return DateFormatter()
    }

    @Provides
    fun provideNotificationWrapper(context: Context, notificationManager: NotificationManager): NotificationManagerWrapper {
        return NotificationManagerWrapperImpl(context, notificationManager)
    }
}
