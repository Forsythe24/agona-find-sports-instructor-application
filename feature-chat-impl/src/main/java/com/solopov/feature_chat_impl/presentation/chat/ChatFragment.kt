package com.solopov.feature_chat_impl.presentation.chat

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solopov.common.R
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.databinding.FragmentChatBinding
import com.solopov.feature_chat_impl.di.ChatFeatureComponent
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ChatFragment : BaseFragment<ChatViewModel>() {

    private lateinit var viewBinding: FragmentChatBinding
    private lateinit var senderId: String
    private lateinit var adapter: ChatAdapter
    private lateinit var currentMessageList: MutableList<MessageItem>

    @Inject
    lateinit var dateFormatter: DateFormatter

    @Inject
    lateinit var router: ChatRouter
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
        viewBinding.chatRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }


    private fun initChatters() {

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ParamsKey.USER, UserCommon::class.java)
        } else {
            arguments?.getSerializable(ParamsKey.USER) as UserCommon?
        }

        user?.let {
            viewModel.setReceiver(it)
            viewModel.setSender()
        }

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

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribe(viewModel: ChatViewModel) {

        with(viewModel) {
            receiverFlow.observe { receiver ->
                with(viewBinding) {
                    receiver?.let {
                        receiverNameTv.text = receiver.name
                        receiver.photo?.let {
                            showImage(it, userImageIv)
                        }
                    }
                }
            }

            senderFlow.observe { sender ->
                with(viewBinding) {

                    val senderRoomId = sender?.uid + viewModel.receiverFlow.value?.uid

                    val receiverRoomId = viewModel.receiverFlow.value?.uid + sender?.uid

                    sender?.let {
                        senderId = it.uid
                        viewModel.downloadMessages(senderRoomId)
                    }

                    sendBtn.setOnClickListener {
                        sender?.let {
                            val date = dateFormatter.formatDateTime(Date())
                            val message = MessageItem("", messageEt.text.toString(), it.uid, date)

                            viewModel.createNewMessage(senderRoomId, message)
                            viewModel.createNewMessage(receiverRoomId, message)

                            currentMessageList.add(message)
                            updateMessages(currentMessageList)
                            adapter.notifyDataSetChanged()

                            messageEt.setText("")
                        }
                    }
                }

            }

            chatFlow.observe { messages ->
                messages?.let {
                    updateMessages(it)
                    currentMessageList = it as MutableList<MessageItem>
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)

                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext()).load(url).into(imageView)

    }
}
