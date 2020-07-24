package com.shukhaev.chatshu.ui.message_recycler_view.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.database.CURRENT_UID
import com.shukhaev.chatshu.ui.message_recycler_view.views.MessageView
import com.shukhaev.chatshu.utils.AppVoicePlayer
import com.shukhaev.chatshu.utils.asTime
import kotlinx.android.synthetic.main.message_item_voice.view.*

class HolderVoiceMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {

    private val mAppVoicePlayer = AppVoicePlayer()

    private val blocUserVoiceMessage = view.bloc_user_voice_message
    private val chatUserVoiceMessageTime = view.message_item_user_voice_message_time

    private val blocIncomingVoiceMessage = view.bloc_incoming_voice_message
    private val chatIncomingVoiceMessageTime = view.message_item_incoming_voice_message_time

    private val chatIncomingBtnPlay = view.message_item_incoming_voice_play
    private val chatIncomingBtnStop = view.message_item_incoming_voice_stop

    private val chatUserBtnPlay = view.message_item_user_voice_play
    private val chatUserBtnStop = view.message_item_user_voice_stop


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blocIncomingVoiceMessage.visibility = View.GONE
            blocUserVoiceMessage.visibility = View.VISIBLE
            chatUserVoiceMessageTime.text =
                view.timeStamp.asTime()
        } else {
            blocIncomingVoiceMessage.visibility = View.VISIBLE
            blocUserVoiceMessage.visibility = View.GONE
            chatIncomingVoiceMessageTime.text =
                view.timeStamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {
        mAppVoicePlayer.init()
        if (view.from == CURRENT_UID) {
            chatUserBtnPlay.setOnClickListener {
                chatUserBtnPlay.visibility = View.GONE
                chatUserBtnStop.visibility = View.VISIBLE
                chatUserBtnStop.setOnClickListener {
                    stop{
                        chatUserBtnStop.setOnClickListener(null)
                        chatUserBtnPlay.visibility = View.VISIBLE
                        chatUserBtnStop.visibility = View.GONE
                    }
                }
                play(view) {
                    chatUserBtnPlay.visibility = View.VISIBLE
                    chatUserBtnStop.visibility = View.GONE
                }

            }
        } else {
            chatIncomingBtnPlay.setOnClickListener {
                chatIncomingBtnPlay.visibility = View.GONE
                chatIncomingBtnStop.visibility = View.VISIBLE
                chatIncomingBtnStop.setOnClickListener {
                    stop{
                        chatIncomingBtnStop.setOnClickListener(null)
                        chatIncomingBtnPlay.visibility = View.VISIBLE
                        chatIncomingBtnStop.visibility = View.GONE
                    }
                }
                play(view) {
                    chatIncomingBtnPlay.visibility = View.VISIBLE
                    chatIncomingBtnStop.visibility = View.GONE
                }
            }
        }
    }

    private fun stop(function: () -> Unit) {
        mAppVoicePlayer.stop {
            function()
        }
    }

    private fun play(view: MessageView, function: () -> Unit) {
        mAppVoicePlayer.play(view.id, view.fileUrl) {
            function()
        }
    }

    override fun onDetach() {
        chatUserBtnPlay.setOnClickListener { null }
        chatIncomingBtnPlay.setOnClickListener { null }
        mAppVoicePlayer.release()
    }
}