package com.shukhaev.chatshu.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.shukhaev.chatshu.MainActivity
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.activities.RegisterActivity
import com.shukhaev.chatshu.utils.AUTH
import com.shukhaev.chatshu.utils.replaceActivity

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
        }
        return true
    }

}