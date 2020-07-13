package com.shukhaev.chatshu

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.shukhaev.chatshu.database.AUTH
import com.shukhaev.chatshu.database.initFirebase
import com.shukhaev.chatshu.database.initUser
import com.shukhaev.chatshu.databinding.ActivityMainBinding
import com.shukhaev.chatshu.ui.fragments.MainFragment
import com.shukhaev.chatshu.ui.fragments.register.EnterPhoneNumberFragment
import com.shukhaev.chatshu.ui.objects.AppDrawer
import com.shukhaev.chatshu.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {
            CoroutineScope(Dispatchers.IO).launch { initContacts() }
            initFields()
            initFunc()
        }
    }



    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser != null) {
            mAppDrawer.create()
            replaceFragment(MainFragment(), false)
        } else {
            replaceFragment(EnterPhoneNumberFragment(),false)
        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }
}