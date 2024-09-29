package com.solopov.common.di.coroutine

import com.solopov.common.di.coroutine.qualifier.DefaultDispatcher
import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.common.di.coroutine.qualifier.MainDispatcher
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
class CoroutineModule {

    @Provides
    @ApplicationScope
    fun scope(
    ): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @ApplicationScope
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @ApplicationScope
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @ApplicationScope
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
