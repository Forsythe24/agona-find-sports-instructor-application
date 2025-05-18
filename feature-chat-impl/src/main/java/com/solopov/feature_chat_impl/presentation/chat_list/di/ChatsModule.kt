package com.solopov.feature_chat_impl.presentation.chat_list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_chat_api.domain.usecase.CreateNewMessageUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadAllUserChatsUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadChatMessagesUseCase
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.presentation.chat_list.ChatsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ChatsModule {
    @Provides
    fun provideChatsViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory,
    ): ChatsViewModel {
        return ViewModelProvider(fragment, factory)[ChatsViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    fun provideViewModel(
        chatMappers: ChatMappers,
        router: ChatRouter,
        resourceManager: ResourceManager,
        dateFormatter: DateFormatter,
        createNewMessageUseCase: CreateNewMessageUseCase,
        loadAllUserChatsUseCase: LoadAllUserChatsUseCase,
    ): ViewModel {
        return ChatsViewModel(
            createNewMessageUseCase = createNewMessageUseCase,
            loadAllUserChatsUseCase = loadAllUserChatsUseCase,
            chatMappers = chatMappers,
            router = router,
            resourceManager = resourceManager,
            dateFormatter = dateFormatter
        )
    }
}
