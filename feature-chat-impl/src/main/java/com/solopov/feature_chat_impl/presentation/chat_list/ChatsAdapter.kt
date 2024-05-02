package com.solopov.feature_chat_impl.presentation.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.solopov.feature_chat_impl.databinding.ItemChatBinding
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import com.solopov.feature_chat_impl.utils.UserMessagesTypes

class ChatsAdapter(
    private val showImage: (url: String, imageView: ImageView) -> Unit,
    private val onItemClicked: (ChatItem) -> Unit,
) : ListAdapter<ChatItem, ChatsViewHolder>(ChatsDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            showImage,
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {

        holder.bindItem(getItem(position))
    }
}
