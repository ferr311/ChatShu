package com.shukhaev.chatshu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.shukhaev.chatshu.activities.RegisterActivity
import com.shukhaev.chatshu.databinding.ActivityMainBinding
import com.shukhaev.chatshu.ui.fragments.ChatFragment
import com.shukhaev.chatshu.ui.objects.AppDrawer
import com.shukhaev.chatshu.utils.replaceActivity
import com.shukhaev.chatshu.utils.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (false) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatFragment())
        } else {
            replaceActivity(RegisterActivity())
        }


    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
    }
}