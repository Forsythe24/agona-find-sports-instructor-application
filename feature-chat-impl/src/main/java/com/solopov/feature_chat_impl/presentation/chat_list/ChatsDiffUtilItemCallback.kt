package com.solopov.feature_chat_impl.presentation.chat_list

import androidx.recyclerview.widget.DiffUtil
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem

class ChatsDiffUtilItemCallback: DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.name == newItem.name && oldItem.photo == newItem.photo
    }

}
