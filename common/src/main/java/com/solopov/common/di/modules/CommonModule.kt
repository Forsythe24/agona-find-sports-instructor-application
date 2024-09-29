package com.solopov.common.di.modules

import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.core.resources.ResourceManagerImpl
import com.solopov.common.data.storage.PreferencesImpl
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.notification.NotificationManagerWrapper
import com.solopov.common.notification.NotificationManagerWrapperImpl
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ParamsKey
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
    fun providePreferences(context: Context): com.solopov.common.core.preferences.Preferences {
        return PreferencesImpl(context)
    }

    @Provides
    @ApplicationScope
    fun provideDataStore(appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(ParamsKey.AUTH_PREFERENCES_KEY) }
        )
    }

    @Provides
    fun provideDateFormatter(): DateFormatter {
        return DateFormatter()
    }

    @Provides
    fun provideNotificationWrapper(
        context: Context,
        notificationManager: NotificationManager,
    ): NotificationManagerWrapper {
        return NotificationManagerWrapperImpl(context, notificationManager)
    }
}
