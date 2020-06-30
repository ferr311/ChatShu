package com.shukhaev.chatshu.ui.fragments

import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        settings_change_bio_editText.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio:String = settings_change_bio_editText.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    showToast(getString(R.string.toast_data_udated))
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }
}