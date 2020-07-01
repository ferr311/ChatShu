package com.shukhaev.chatshu.ui.fragments

import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.APP_ACTIVITY


class ChatFragment : Fragment(R.layout.fragment_chat) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }

}