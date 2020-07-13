package com.shukhaev.chatshu.ui.fragments.single_chat

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DatabaseReference
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.database.*
import com.shukhaev.chatshu.models.CommonModel
import com.shukhaev.chatshu.models.UserModel
import com.shukhaev.chatshu.ui.fragments.BaseFragment
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppChildEventListener
    private var mCountMessages = 15
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onResume() {
        super.onResume()
        initFields()
        hideKeyboard()
        initToolbar()
        initRecyclerView()
    }

    private fun initFields() {
        mSwipeRefreshLayout = single_chat_swipeRefresh
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecyclerView() {
        mRecyclerView = single_chat_recycler
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID).child(contact.id)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false

        mMessagesListener = AppChildEventListener {
            val message = it.getCommonModel()
            if (mSmoothScrollToPosition) {
                mAdapter.addItemToBottom(message) {
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)    //автопрокрутка ресайклера
                }
            } else {
                mAdapter.addItemToTop(message) {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }

        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsScrolling && dy < 0 && mLayoutManager.findFirstVisibleItemPosition() <= 3) {
                    updateData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true
                }

            }
        })

        mSwipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        mSmoothScrollToPosition = false
        mIsScrolling = false
        mCountMessages += 10
        mRefMessages.removeEventListener(mMessagesListener)
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.main_activity_toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            updateInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(
            NODE_USERS
        ).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)

        single_chat_btn_send.setOnClickListener {
            mSmoothScrollToPosition = true
            val message = single_chat_input_message.text.toString()
            if (message.isEmpty()) {
                showToast(getString(R.string.error_message_is_empty))
            } else sendMessage(
                message,
                contact.id,
                TYPE_TEXT
            ) {
                single_chat_input_message.setText("")
            }
        }
    }

    private fun updateInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()) {
            mToolbarInfo.toolbar_fullname.text = contact.fullname
        } else mToolbarInfo.toolbar_fullname.text = mReceivingUser.fullname

        mToolbarInfo.toolbar_image.downloadAndSetImage(mReceivingUser.photoUrl)

        mToolbarInfo.toolbar_status.text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }
}