package com.shukhaev.chatshu.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.database.*
import com.shukhaev.chatshu.models.CommonModel
import com.shukhaev.chatshu.utils.APP_ACTIVITY
import com.shukhaev.chatshu.utils.AppValueEventListener
import com.shukhaev.chatshu.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_list.*


class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()


    override fun onResume() {

        super.onResume()
        APP_ACTIVITY.title = "Chat Shu"
        hideKeyboard()
        APP_ACTIVITY.mAppDrawer.enableDrawer()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_list_recycler
        mAdapter = MainListAdapter()
        // Первый запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->
                //Второй запрос
                mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot1 ->
                    val newModel = dataSnapshot1.getCommonModel()
                    //Третий запрос
                    mRefMessages.child(model.id).limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot2 ->
                            val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                            if (tempList.isEmpty()){
                                newModel.lastMessage = "Чат очищен"
                            }else{
                                newModel.lastMessage = tempList[0].text
                            }

                            if (newModel.fullname.isEmpty()){
                                newModel.fullname = newModel.phone
                            }
                            mAdapter.updateListItems(newModel)
                        })
                })
            }
        })

        mRecyclerView.adapter = mAdapter
    }

}