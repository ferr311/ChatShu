package com.shukhaev.chatshu.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.models.CommonModel
import com.shukhaev.chatshu.database.CURRENT_UID
import com.shukhaev.chatshu.utils.DiffUtilCallback
import com.shukhaev.chatshu.utils.asTime
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessagesCache = mutableListOf<CommonModel>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {

        val blocUserMessage = view.bloc_user_message
        val chatUserMessageText = view.message_item_user_message_text
        val chatUserMessageTime = view.message_item_user_message_time

        val blocIncomingMessage = view.bloc_incoming_message
        val chatIncomingMessageText = view.message_item_incoming_message_text
        val chatIncomingMessageTime = view.message_item_incoming_message_time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocIncomingMessage.visibility = View.GONE
            holder.chatUserMessageText.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text =
                mListMessagesCache[position].timeStamp.toString().asTime()
        } else {
            holder.blocUserMessage.visibility = View.GONE
            holder.blocIncomingMessage.visibility = View.VISIBLE
            holder.chatIncomingMessageText.text = mListMessagesCache[position].text
            holder.chatIncomingMessageTime.text =
                mListMessagesCache[position].timeStamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: CommonModel, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            notifyItemInserted(mListMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: CommonModel, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            mListMessagesCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}


