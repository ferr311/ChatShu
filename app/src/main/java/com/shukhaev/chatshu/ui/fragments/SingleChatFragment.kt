package com.shukhaev.chatshu.ui.fragments

import android.view.View
import com.google.firebase.database.DatabaseReference
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.models.CommonModel
import com.shukhaev.chatshu.models.UserModel
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference

    override fun onResume() {
        super.onResume()
        mToolbarInfo = APP_ACTIVITY.mToolbar.main_activity_toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            updateInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }

    private fun updateInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()){
            mToolbarInfo.toolbar_fullname.text = contact.fullname
        }else mToolbarInfo.toolbar_fullname.text = mReceivingUser.fullname

        mToolbarInfo.toolbar_image.downloadAndSetImage(mReceivingUser.photoUrl)

        mToolbarInfo.toolbar_status.text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }
}