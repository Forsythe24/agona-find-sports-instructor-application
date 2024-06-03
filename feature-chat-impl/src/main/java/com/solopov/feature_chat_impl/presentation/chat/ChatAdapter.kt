package com.solopov.feature_chat_impl.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.feature_chat_impl.databinding.ItemDataMessageBinding
import com.solopov.feature_chat_impl.databinding.ItemReceivedMessageBinding
import com.solopov.feature_chat_impl.databinding.ItemSentMessageBinding
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.utils.UserMessagesTypes

class ChatAdapter(
    private val currentSenderId: String,
) : ListAdapter<MessageItem, ViewHolder>(MessageDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            UserMessagesTypes.RECEIVED_MESSAGE.number -> {
                ReceivedMessageViewHolder(
                    ItemReceivedMessageBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            UserMessagesTypes.SENT_MESSAGE.number -> {
                SentMessageViewHolder(
                    ItemSentMessageBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            else -> {
                DataMessageViewHolder(
                    ItemDataMessageBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SentMessageViewHolder -> {
                getItem(position)?.let { holder.bindItem(it) }
            }

            is ReceivedMessageViewHolder -> {
                getItem(position)?.let { holder.bindItem(it) }
            }

            is DataMessageViewHolder -> {
                getItem(position)?.let { holder.bindItem(it) }
            }

            else -> Unit
        }
    }

    class SentMessageViewHolder(
        private val viewBinding: ItemSentMessageBinding
    ) : ViewHolder(viewBinding.root) {
        fun bindItem(messageItem: MessageItem) {
            with(viewBinding) {
                with(messageItem) {
                    sentMessageTv.text = text
                    dateTv.text = date.split(" ")[1]
                }
            }
        }
    }

    class ReceivedMessageViewHolder(
        private val viewBinding: ItemReceivedMessageBinding
    ) : ViewHolder(viewBinding.root) {
        fun bindItem(messageItem: MessageItem) {
            with(viewBinding) {
                with(messageItem) {
                    receivedMessageTv.text = text
                    dateTv.text = date.split(" ")[1]
                }
            }
        }
    }

    class DataMessageViewHolder(
        private val viewBinding: ItemDataMessageBinding
    ) : ViewHolder(viewBinding.root) {

        fun bindItem(messageItem: MessageItem) {
            viewBinding.tvDate.text = messageItem.date
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return when (currentItem?.senderId) {
            currentSenderId -> UserMessagesTypes.SENT_MESSAGE.number
            "" -> UserMessagesTypes.DATA_MESSAGE.number
            else -> UserMessagesTypes.RECEIVED_MESSAGE.number
        }
    }

}
