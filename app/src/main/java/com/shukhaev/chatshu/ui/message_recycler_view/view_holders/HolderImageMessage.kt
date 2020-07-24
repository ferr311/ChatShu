package com.shukhaev.chatshu.ui.message_recycler_view.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.database.CURRENT_UID
import com.shukhaev.chatshu.ui.message_recycler_view.views.MessageView
import com.shukhaev.chatshu.utils.asTime
import com.shukhaev.chatshu.utils.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item_image.view.*

class HolderImageMessage(view: View) : RecyclerView.ViewHolder(view),MessageHolder {

    private val blocUserImageMessage = view.bloc_user_image_message
    private val chatUserImageMessageTime = view.message_item_user_image_message_time
    private val chatUserImage = view.message_item_user_image

    private val blocIncomingImageMessage = view.bloc_incoming_image_message
    private val chatIncomingImageMessageTime = view.message_item_incoming_image_message_time
    private val chatIncomingImage = view.message_item_incoming_image


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blocIncomingImageMessage.visibility = View.GONE
            blocUserImageMessage.visibility = View.VISIBLE
            chatUserImage.downloadAndSetImage(view.fileUrl)
            chatUserImageMessageTime.text =
                view.timeStamp.asTime()
        } else {
            blocIncomingImageMessage.visibility = View.VISIBLE
            blocUserImageMessage.visibility = View.GONE
            chatIncomingImage.downloadAndSetImage(view.fileUrl)
            chatIncomingImageMessageTime.text =
                view.timeStamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {

    }

    override fun onDetach() {

    }


}