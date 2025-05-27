package com.solopov.feature_chat_impl.presentation.chat.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem

class MessageDiffUtilItemCallback : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id;
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.senderId == newItem.senderId && oldItem.text == newItem.text && oldItem.date == newItem.date
    }

}
