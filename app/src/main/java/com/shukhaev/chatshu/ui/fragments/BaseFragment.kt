package com.shukhaev.chatshu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.MainActivity
import com.shukhaev.chatshu.utils.APP_ACTIVITY
import java.util.zip.Inflater
//Базовый фрагмент, от него наследуются все фрагменты кроме главного
open class BaseFragment(private val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()

    }
}