package com.shukhaev.chatshu.ui.fragments

import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Изменить имя"
        initFullNameList()
    }

    private fun initFullNameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size > 1) {
            settings_change_name_editText.setText(fullnameList[0])
            settings_change_surname_editText.setText(fullnameList[1])
        } else {
            settings_change_name_editText.setText(fullnameList[0])
        }
    }

    override fun change() {
        val name = settings_change_name_editText.text.toString()
        val surname = settings_change_surname_editText.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME).setValue(fullname)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_udated))
                        USER.fullname = fullname
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}