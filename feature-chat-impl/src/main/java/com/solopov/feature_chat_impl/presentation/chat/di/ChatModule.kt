package com.solopov.feature_chat_impl.presentation.chat.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_chat_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadChatMessagesUseCase
import com.solopov.feature_chat_api.domain.usecase.SendMessageUseCase
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.feature_chat_impl.data.network.StompManager
import com.solopov.feature_chat_impl.presentation.chat.ChatViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ChatModule {
    @Provides
    fun provideChatViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory,
    ): ChatViewModel {
        return ViewModelProvider(fragment, factory)[ChatViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    fun provideViewModel(
        chatMappers: ChatMappers,
        messageMappers: MessageMappers,
        router: ChatRouter,
        dateFormatter: DateFormatter,
        resManager: ResourceManager,
        stompManager: StompManager,
        resourceManager: ResourceManager,
        getCurrentUserUseCase: GetCurrentUserUseCase,
        sendMessageUseCase: SendMessageUseCase,
        loadChatMessagesUseCase: LoadChatMessagesUseCase
    ): ViewModel {
        return ChatViewModel(
            chatMappers = chatMappers,
            messageMappers = messageMappers,
            router = router,
            dateFormatter = dateFormatter,
            resManager = resManager,
            stompManager = stompManager,
            resourceManager = resourceManager,
            getCurrentUserUseCase = getCurrentUserUseCase,
            sendMessageUseCase = sendMessageUseCase,
            loadChatMessagesUseCase = loadChatMessagesUseCase
        )
    }
}
