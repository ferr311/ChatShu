package com.shukhaev.chatshu.ui.screens

import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.utils.APP_ACTIVITY

//Базовый фрагмент, от него наследуются все фрагменты кроме главного
open class BaseFragment(private val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()

    }
}