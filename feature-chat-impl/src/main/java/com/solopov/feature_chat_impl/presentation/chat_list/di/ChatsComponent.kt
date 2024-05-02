package com.solopov.feature_chat_impl.presentation.chat_list.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_chat_impl.presentation.chat_list.ChatsFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(
    modules = [
        ChatsModule::class
    ]
)
@ScreenScope
interface ChatsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): ChatsComponent
    }

    fun inject(fragment: ChatsFragment)
}
