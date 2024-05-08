package com.solopov.feature_chat_impl.presentation.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solopov.common.R
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.databinding.FragmentChatBinding
import com.solopov.feature_chat_impl.di.ChatFeatureComponent
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.presentation.chat_list.ChatsFragment
import com.solopov.feature_chat_impl.utils.Constants.MESSAGE_UPDATE_INTERVAL
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class ChatFragment : BaseFragment<ChatViewModel>() {

    private lateinit var viewBinding: FragmentChatBinding
    private lateinit var senderId: String
    private lateinit var adapter: ChatAdapter
    private lateinit var currentMessageList: MutableList<MessageItem>
    private lateinit var currentMessageListWithDates: MutableList<MessageItem>

    @Inject
    lateinit var dateFormatter: DateFormatter

    @Inject
    lateinit var router: ChatRouter

    @Inject
    lateinit var chatMappers: ChatMappers
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

            backBtn.setOnClickListener {
                router.goBack()
            }
        }


    }

    override fun onStart() {
        repeatCheckingMessagesForUpdates()
        super.onStart()
    }

    override fun onStop() {
        viewModel.stopRepeatWork()
        super.onStop()
    }
    // фото через crop

    private fun repeatCheckingMessagesForUpdates() {

        viewModel.let { vm ->
            vm.doRepeatWork(
                MESSAGE_UPDATE_INTERVAL
            ) {
                getMessages()
            }
        }


    }

    private fun getMessages() {
        val sender = viewModel.senderFlow.value
        val receiver = viewModel.receiverFlow.value

        if (receiver != null && sender != null) {

            val receiverId = receiver.userId
            senderId = sender.userId

            val senderRoomId = senderId + receiverId

            // viewModel.getRecentMessages()
            viewModel.downloadMessages(senderId, senderRoomId)
        }
    }


    private fun initChatters() {

        val chatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ParamsKey.CHAT, ChatCommon::class.java)
        } else {
            arguments?.getSerializable(ParamsKey.CHAT) as ChatCommon?
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

    //    private suspend fun updateRecentMessages(messages: PagingData<MessageItem>) {
//        with(viewBinding) {
//            if (chatRv.adapter == null) {
//                adapter = ChatAdapter(senderId)
//                chatRv.adapter = adapter
//            }
//            (chatRv.adapter as ChatAdapter).(viewLifecycleOwner.lifecycle, messages)
//
//        }
//    }
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
            receiverFlow.observe { receiver ->
                with(viewBinding) {
                    receiver?.let {
                        receiverNameTv.text = receiver.name
                        receiver.photo?.let {
                            showImage(it, userImageIv)
                        }

                        val onClickListener = OnClickListener {
                            router.openUserProfile(chatMappers.mapChatItemToChatCommon(receiver))
                        }
                        userImageCv.setOnClickListener(onClickListener)
                        receiverNameTv.setOnClickListener(onClickListener)
                    }
                }
            }

            senderFlow.observe { sender ->
                with(viewBinding) {

                    val receiver = viewModel.receiverFlow.value

                    if (receiver != null && sender != null) {

                        val receiverId = receiver.userId
                        senderId = sender.userId

                        val senderRoomId = senderId + receiverId
                        val receiverRoomId = receiverId + senderId

                        sendBtn.setOnClickListener {

                            sender.let {
                                val date = dateFormatter.formatDateTime(Date())
                                val message =
                                    MessageItem("", messageEt.text.toString(), it.userId, date)

                                messageEt.setText("")

                                viewModel.createNewMessage(senderId, senderRoomId, message)
                                viewModel.createNewMessage(receiverId, receiverRoomId, message)

                                currentMessageList.add(message)
                                updateMessagesWithRespectToDate()

                                viewBinding.chatRv.smoothScrollToPosition(adapter.itemCount)
                            }
                        }
                    }

                }

            }

            chatFlow.observe { messages ->
                messages?.let {
                    val messagesWithDates = it.addDates()
                    updateMessages(messagesWithDates)
                    viewBinding.chatRv.scrollToPosition(adapter.itemCount - 1)
                    currentMessageList = it.toMutableList()
                    currentMessageListWithDates = messagesWithDates.toMutableList()
                }
            }
//            viewModel.getRecentMessages()

//            messageFlow.observe { messages ->
//                messages?.let {
//                    updateMessages(it)
//                }
//
//            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)

                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateMessagesWithRespectToDate() {
        // adding dates only when it's the first message of the chat or when it's a new day
        if (currentMessageList.size == 1 || currentMessageList.size > 1
            && dateFormatter.parseStringToDate(currentMessageList[currentMessageList.size - 2].date) // previous last date
            != dateFormatter.parseStringToDate(currentMessageList.last().date)
        ) // last date
        {
            currentMessageListWithDates = currentMessageList.addDates() as MutableList<MessageItem>
            updateMessages(currentMessageListWithDates)

        } else {
            updateMessages(currentMessageListWithDates)
        }
    }


    private fun List<MessageItem>.addDates(): List<MessageItem> {
        if (this.isEmpty()) {
            return this
        }
        // 0 by default because the first message in a chat always needs its' date above it
        val dateIndices = mutableListOf(0)
        var offset = 1
        for (i in 0 until this.size - 1) {
            val currMessageDate =
                dateFormatter.formatDate(dateFormatter.parseStringToDate(this[i].date)!!)
            val nextMessageDate =
                dateFormatter.formatDate(dateFormatter.parseStringToDate(this[i + 1].date)!!)
            if (currMessageDate != nextMessageDate) {
                dateIndices.add(i + 1 + offset)
                offset++
            }
        }
        val mutableMessages = this.toMutableList()

        val todayString = dateFormatter.formatDateTo_ddMMMyyyy_DateFormat(Date())

        dateIndices.forEach { index ->
            with(mutableMessages[index]) {

                var chatDate = dateFormatter.formatDateTo_ddMMMyyyy_DateFormat(
                    dateFormatter.parseStringToDate(date)!!
                )

                if (todayString == chatDate) {
                    //change to "today" if the chat date and the current date match
                    chatDate =
                        requireContext().getString(com.solopov.feature_chat_impl.R.string.today)
                }

                mutableMessages.add(
                    index,
                    MessageItem(
                        id = "",
                        text = "",
                        senderId = "",
                        date = chatDate
                    )
                )
            }
        }

        return mutableMessages
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext()).load(url).optionalCircleCrop().into(imageView)
    }
}
