package com.solopov.feature_chat_impl.presentation.chat_list.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem

class ChatsDiffUtilItemCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }

}
