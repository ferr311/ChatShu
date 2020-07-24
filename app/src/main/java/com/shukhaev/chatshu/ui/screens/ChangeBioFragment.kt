package com.shukhaev.chatshu.ui.screens

import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.database.*
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "О себе"
        settings_change_bio_editText.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio:String = settings_change_bio_editText.text.toString()
        setBioToDatabase(newBio)
    }
}