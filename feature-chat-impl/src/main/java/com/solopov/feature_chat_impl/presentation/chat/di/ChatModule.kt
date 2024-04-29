package com.solopov.feature_chat_impl.presentation.chat.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.feature_chat_impl.presentation.chat.ChatViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ChatModule {
    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): ChatViewModel {
        return ViewModelProvider(fragment, factory)[ChatViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    fun provideSignInViewModel(interactor: ChatInteractor, exceptionHandlerDelegate: ExceptionHandlerDelegate, chatMappers: ChatMappers, messageMappers: MessageMappers): ViewModel {
        return ChatViewModel(interactor, exceptionHandlerDelegate, chatMappers, messageMappers)
    }
}
