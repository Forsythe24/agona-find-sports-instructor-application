package com.solopov.feature_chat_impl.presentation.chat.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_chat_impl.presentation.chat.ChatFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(
    modules = [
        ChatModule::class
    ]
)
@ScreenScope
interface ChatComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): ChatComponent
    }

    fun inject(fragment: ChatFragment)
}
