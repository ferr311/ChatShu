package com.shukhaev.chatshu.ui.screens

import android.view.*
import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.MainActivity
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.hideKeyboard


open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mAppDrawer.disableDrawer()
        setHasOptionsMenu(true)
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}