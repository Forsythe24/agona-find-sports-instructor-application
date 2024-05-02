package com.solopov.feature_chat_impl.presentation.chat_list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.presentation.chat.ChatViewModel
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
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): ChatsViewModel {
        return ViewModelProvider(fragment, factory)[ChatsViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    fun provideSignInViewModel(interactor: ChatInteractor, exceptionHandlerDelegate: ExceptionHandlerDelegate, chatMappers: ChatMappers): ViewModel {
        return ChatsViewModel(interactor, exceptionHandlerDelegate, chatMappers)
    }
}
