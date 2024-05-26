package com.solopov.feature_chat_impl.presentation.chat_list

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.feature_chat_impl.databinding.ItemChatBinding
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem

class ChatsViewHolder(
    private val viewBinding: ItemChatBinding,
    private val showImage: (url: String, imageView: ImageView) -> Unit,
    private val onItemClicked: (ChatItem) -> Unit,

    ) : ViewHolder(viewBinding.root) {

    fun bindItem(item: ChatItem) {
        with(item) {
            with(viewBinding) {
                photo?.let { showImage(it, userImageIv) }
                nameTv.text = name
                root.setOnClickListener {
                    onItemClicked(item)
                }
                userFriendlyLastMessageDate?.let {
                    lastMessageDateTv.text = it
                }

                nameTv.text = nameTv.text.let {
                    if (it.length > 24) {
                        "${it.substring(0, 23)}..."
                    } else {
                        it
                    }
                }

                lastMessageText?.let {
                    lastMessagePreviewTv.text = if (it.length > 37) {
                        "${it.substring(0, 36)}..."
                    } else {
                        it
                    }
                }
            }
        }
    }
}
