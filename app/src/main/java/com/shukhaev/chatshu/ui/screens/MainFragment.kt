package com.shukhaev.chatshu.ui.screens

import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.APP_ACTIVITY
import com.shukhaev.chatshu.utils.hideKeyboard


class MainFragment : Fragment(R.layout.fragment_chat) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Chat Shu"
        hideKeyboard()
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }

}