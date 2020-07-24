package com.shukhaev.chatshu.ui.message_recycler_view.view_holders

import android.os.Environment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.database.CURRENT_UID
import com.shukhaev.chatshu.database.getFileFromStorage
import com.shukhaev.chatshu.ui.message_recycler_view.views.MessageView
import com.shukhaev.chatshu.utils.WRITE_FILES
import com.shukhaev.chatshu.utils.asTime
import com.shukhaev.chatshu.utils.checkPermission
import com.shukhaev.chatshu.utils.showToast
import kotlinx.android.synthetic.main.message_item_file.view.*
import java.io.File
import java.lang.Exception

class HolderFileMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {

    private val blocUserFileMessage = view.bloc_user_file_message
    private val chatUserFileMessageTime = view.message_item_user_file_message_time

    private val blocIncomingFileMessage = view.bloc_incoming_file_message
    private val chatIncomingFileMessageTime = view.message_item_incoming_file_message_time

    private val chatUserFileName = view.message_item_user_filename
    private val chatUserBtnDownload = view.message_item_user_file_download
    private val chatUserProgressBar = view.message_item_user_progress_bar

    private val chatIncomingFileName = view.message_item_incoming_filename
    private val chatIncomingBtnDownload = view.message_item_incoming_file_download
    private val chatIncomingProgressBar = view.message_item_incoming_progress_bar


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blocIncomingFileMessage.visibility = View.GONE
            blocUserFileMessage.visibility = View.VISIBLE
            chatUserFileMessageTime.text = view.timeStamp.asTime()
            chatUserFileName.text = view.text
        } else {
            blocIncomingFileMessage.visibility = View.VISIBLE
            blocUserFileMessage.visibility = View.GONE
            chatIncomingFileMessageTime.text = view.timeStamp.asTime()
            chatIncomingFileName.text = view.text
        }
    }

    override fun onAttach(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBtnDownload.setOnClickListener { clickToBtnFile(view) }
        } else {
            chatIncomingBtnDownload.setOnClickListener { clickToBtnFile(view) }
        }
    }

    private fun clickToBtnFile(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBtnDownload.visibility = View.INVISIBLE
            chatUserProgressBar.visibility = View.VISIBLE
        } else {
            chatIncomingBtnDownload.visibility = View.INVISIBLE
            chatIncomingProgressBar.visibility = View.VISIBLE
        }

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            view.text
        )

        try {
            if (checkPermission(WRITE_FILES)) {
                file.createNewFile()
                getFileFromStorage(file, view.fileUrl){
                    if (view.from == CURRENT_UID) {
                        chatUserBtnDownload.visibility = View.VISIBLE
                        chatUserProgressBar.visibility = View.INVISIBLE
                    } else {
                        chatIncomingBtnDownload.visibility = View.VISIBLE
                        chatIncomingProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (e:Exception) {
            showToast(e.message.toString())
        }
    }

    override fun onDetach() {
        chatUserBtnDownload.setOnClickListener(null)
        chatIncomingBtnDownload.setOnClickListener(null)
    }
}