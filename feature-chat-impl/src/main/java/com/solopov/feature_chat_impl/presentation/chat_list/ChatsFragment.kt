package com.solopov.feature_chat_impl.presentation.chat_list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.copy
import com.bumptech.glide.Glide
import com.solopov.common.R
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.ChatCommon
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.databinding.FragmentChatListBinding
import com.solopov.feature_chat_impl.di.ChatFeatureComponent
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class ChatsFragment : BaseFragment<ChatsViewModel>() {

    private lateinit var viewBinding: FragmentChatListBinding

    @Inject
    lateinit var chatRouter: ChatRouter

    @Inject
    lateinit var dateFormatter: DateFormatter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChatListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUser()
    }

    private fun initUser() = viewModel.setUser()

    private fun initChats(userId: String) = viewModel.getAllChatsByUserId(userId)

    override fun initViews() {
        viewBinding.chatsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun updateChatList(chats: List<ChatItem>) {
        with(viewBinding) {
            if (chatsRv.adapter == null) {
                chatsRv.adapter = ChatsAdapter(::showImage, ::onItemClicked)
            }
            (chatsRv.adapter as ChatsAdapter).submitList(chats)

        }
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)

    }

    private fun onItemClicked(chat: ChatItem) {
        with(chat) {
            chatRouter.openChat(
                ChatCommon(
                    userId,
                    name,
                    photo
                )
            )
        }
    }
    override fun inject() {
        FeatureUtils.getFeature<ChatFeatureComponent>(this, ChatFeatureApi::class.java)
            .chatsComponentFactory().create(this).inject(this)
    }

    override fun subscribe(viewModel: ChatsViewModel) {
        with(viewModel) {
            chatsFlow.observe { chats ->
                chats?.let {
                    updateChatList(it.date())
                }
            }

            userFlow.observe { user ->
                user?.let {
                    initChats(user.userId)
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

    private fun List<ChatItem>.date(): List<ChatItem> {

        this.map { chatItem ->
            val date = dateFormatter.parseStringToDateTime(chatItem.lastMessageDate!!)!!
            val dateString = dateFormatter.formatDate(date)
            val now = Date()
            val todayDateString = dateFormatter.formatDate(now)

            if (dateString == todayDateString) {
                chatItem.userFriendlyLastMessageDate = dateFormatter.formatDateTime(date).split(" ")[1]
            }

            val c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            val c2 = Calendar.getInstance();
            c2.time = date;

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                chatItem.userFriendlyLastMessageDate = "Yesterday"
            }
        }
//        println(this)
//        println(result)
        return this
    }

}
