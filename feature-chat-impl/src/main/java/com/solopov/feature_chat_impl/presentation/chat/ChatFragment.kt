package com.solopov.feature_chat_impl.presentation.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_impl.databinding.FragmentChatBinding
import com.solopov.feature_chat_impl.di.ChatFeatureComponent
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.presentation.chat.recycler_view.ChatAdapter
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import java.util.Date

class ChatFragment : BaseFragment<ChatViewModel>() {

    private lateinit var viewBinding: FragmentChatBinding
    private lateinit var senderId: String
    private lateinit var receiverId: String
    private lateinit var adapter: ChatAdapter
    private lateinit var currentMessageList: MutableList<MessageItem>
    private lateinit var currentMessageListWithDates: MutableList<MessageItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentChatBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChatters()
    }

    override fun initViews() {
        with(viewBinding) {
            chatRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            sendBtn.setOnClickListener {

                val senderRoomId = senderId + receiverId
                val receiverRoomId = receiverId + senderId

                val date = viewModel.formatDateTime(Date())
                val sentMessage =
                    MessageItem(
                        null,
                        senderRoomId,
                        messageEt.text.toString(),
                        senderId,
                        date
                    )

                val receivedMessage =
                    MessageItem(
                        null,
                        receiverRoomId,
                        messageEt.text.toString(),
                        senderId,
                        date
                    )

                messageEt.setText("")

                viewModel.createNewMessage(senderId, sentMessage)
                viewModel.createNewMessage(receiverId, receivedMessage)

                currentMessageList.add(sentMessage)
                updateMessagesWithRespectToDate()

                chatRv.smoothScrollToPosition(adapter.itemCount)
            }

            val onReceiverClickListener = OnClickListener {
                viewModel.receiverState.value?.let {
                    viewModel.openUserProfile(it.userId)
                }

            }
            userImageCv.setOnClickListener(onReceiverClickListener)
            receiverNameTv.setOnClickListener(onReceiverClickListener)

            viewBinding.backBtn.setOnClickListener {
                viewModel.goBack()
            }

            setUpScheduleEventButton()
        }
    }

    private fun initChatters() {
        val chatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ParamsKey.CHAT_KEY, ChatCommon::class.java)
        } else {
            arguments?.getSerializable(ParamsKey.CHAT_KEY) as ChatCommon?
        }

        chatter?.let {
            viewModel.setReceiver(it)
        }
        viewModel.setSender()

    }

    override fun inject() {
        FeatureUtils.getFeature<ChatFeatureComponent>(this, ChatFeatureApi::class.java)
            .chatComponentFactory().create(this).inject(this)
    }

    private fun updateMessages(messages: List<MessageItem>) {
        with(viewBinding) {
            if (chatRv.adapter == null) {
                adapter = ChatAdapter(senderId)
                chatRv.adapter = adapter
            }
            (chatRv.adapter as ChatAdapter).submitList(messages)

        }
    }

    override fun subscribe(viewModel: ChatViewModel) {

        with(viewModel) {
            receiverState.observe { receiver ->
                receiver?.let {
                    receiverId = receiver.userId
                    initReceiverViews(receiver)
                }
            }

            senderState.observe { sender ->
                sender?.let {
                    senderId = sender.userId
                }

                if (isMessageListeningStarted.not()) {
                    val receiver = viewModel.receiverState.value

                    if (receiver != null && sender != null) {

                        val receiverId = receiver.userId
                        senderId = sender.userId

                        val senderRoomId = senderId + receiverId

                        downloadMessages(senderRoomId)
                        initMessageReceiving(senderRoomId)
                    }
                }

            }

            chatState.observe { messages ->
                viewBinding.scheduleEventBtn.let {
                    if (messages.isNullOrEmpty()) {
                        it.visibility = GONE
                    } else {
                        it.visibility = VISIBLE
                    }
                }


                messages?.let {
                    val messagesWithDates = viewModel.addDates(it)
                    updateMessages(messagesWithDates)
                    viewBinding.chatRv.scrollToPosition(adapter.itemCount - 1)
                    currentMessageList = it.toMutableList()
                    currentMessageListWithDates = messagesWithDates.toMutableList()
                }
            }

            message.observe { message ->
                Snackbar.make(viewBinding.root, message.text, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun initReceiverViews(receiver: ChatItem) {
        with(viewBinding) {
            receiverNameTv.text = receiver.name.let {
                if (it.length > 20) {
                    "${it.substring(0, 19)}..."
                } else {
                    it
                }
            }
            receiver.photo.let {
                if (!it.isNullOrEmpty()) {
                    showImage(it, userImageIv)
                }
            }

        }
    }

    private fun setUpScheduleEventButton() {
        viewBinding.scheduleEventBtn.setOnClickListener {
            viewModel.receiverState.value?.let {
                viewModel.goToEventCalendar(it.name)
            }
        }
    }

    private fun updateMessagesWithRespectToDate() {
        // adding dates only when it's the first message of the chat or when it's a new day
        if (currentMessageList.size == 1 || currentMessageList.size > 1
            && viewModel.parseStringToDate(currentMessageList[currentMessageList.size - 2].date) // previous last date
            != viewModel.parseStringToDate(currentMessageList.last().date)
        ) // last date
        {
            currentMessageListWithDates = viewModel.addDates(currentMessageList) as MutableList<MessageItem>
            updateMessages(currentMessageListWithDates)

        } else {
            updateMessages(currentMessageListWithDates)
        }
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext()).load(url).into(imageView)
    }
}
