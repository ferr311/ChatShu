package com.shukhaev.chatshu.ui.message_recycler_view.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.database.CURRENT_UID
import com.shukhaev.chatshu.ui.message_recycler_view.views.MessageView
import com.shukhaev.chatshu.utils.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class HolderTextMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {

    private val blocUserMessage = view.bloc_user_message
    private val chatUserMessageText = view.message_item_user_message_text
    private val chatUserMessageTime = view.message_item_user_message_time

    private val blocIncomingMessage = view.bloc_incoming_message
    private val chatIncomingMessageText = view.message_item_incoming_message_text
    private val chatIncomingMessageTime = view.message_item_incoming_message_time


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blocUserMessage.visibility = View.VISIBLE
            blocIncomingMessage.visibility = View.GONE
            chatUserMessageText.text = view.text
            chatUserMessageTime.text =
                view.timeStamp.asTime()
        } else {
            blocUserMessage.visibility = View.GONE
            blocIncomingMessage.visibility = View.VISIBLE
            chatIncomingMessageText.text = view.text
            chatIncomingMessageTime.text =
                view.timeStamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {

    }

    override fun onDetach() {

    }
}