package com.shukhaev.chatshu.ui.fragments

import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Изменить имя пользователя"
        settings_change_username_editText.setText(USER.username)
    }

    override fun change() {
        mNewUsername =
            settings_change_username_editText.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES).addListenerForSingleValueEvent(AppValueEventListener{
                if (it.hasChild(mNewUsername)){
                    showToast("Такой пользователь уже существует")
                }else{
                    changeUsername()
                }
            })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME).setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    deleteOldUsername()
                    showToast(getString(R.string.toast_data_udated))
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    showToast(getString(R.string.toast_data_udated))
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                }
            }
    }
}