package com.solopov.feature_chat_impl.presentation.chat_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
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
import com.solopov.feature_chat_api.di.ChatFeatureApi
import com.solopov.feature_chat_impl.databinding.FragmentChatListBinding
import com.solopov.feature_chat_impl.di.ChatFeatureComponent
import com.solopov.feature_chat_impl.presentation.chat_list.ChatsViewModel.Companion.MESSAGE_SYNC_INTERVAL
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem

class ChatsFragment : BaseFragment<ChatsViewModel>() {

    private lateinit var viewBinding: FragmentChatListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentChatListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private fun initChats(userId: String) = viewModel.getAllChatsByUserId(userId)

    override fun initViews() {
        viewBinding.chatsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun updateChatList(chats: List<ChatItem>) {
        with(viewBinding) {
            if (chatsRv.adapter == null) {
                chatsRv.adapter = ChatsAdapter(::showImage, ::onItemClicked)
            }
            (chatsRv.adapter as ChatsAdapter).submitList(chats)
        }
    }

    private fun onItemClicked(chat: ChatItem) {
        with(chat) {
            viewModel.openChat(
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
            chatsState.observe { chats ->
                viewBinding.noChatsYetTv.let {
                    if (chats.isNullOrEmpty()) {
                        it.visibility = VISIBLE
                    } else {
                        it.visibility = GONE
                    }
                }

                chats?.let {
                    updateChatList(viewModel.addDates(it))
                }
            }

            userState.observe { user ->
                user?.let {
                    initChats(user.userId)
                }
            }

            message.observe { message ->
                Snackbar.make(viewBinding.root, message.text, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onStop() {
        viewModel.stopRepeatWork()
        super.onStop()
    }

    override fun onStart() {
        repeatCheckingMessagesForUpdates()
        super.onStart()
    }

    private fun repeatCheckingMessagesForUpdates() {
        with(viewModel) {
            doRepeatWork(
                MESSAGE_SYNC_INTERVAL
            ) {
                userState.value?.let {
                    getAllChatsByUserId(it.userId)
                }
            }
        }
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)

    }
}
