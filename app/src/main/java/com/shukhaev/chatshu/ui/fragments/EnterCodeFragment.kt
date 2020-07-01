package com.shukhaev.chatshu.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.shukhaev.chatshu.MainActivity
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.activities.RegisterActivity
import com.shukhaev.chatshu.utils.*
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(private val phoneNumber: String, private val id: String) :
    Fragment(R.layout.fragment_enter_code) {

    override fun onResume() {
        super.onResume()
    }


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dataMap = mutableMapOf<String, Any>()
                dataMap[CHILD_ID] = uid
                dataMap[CHILD_PHONE] = phoneNumber
                dataMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                (activity as RegisterActivity).replaceActivity(MainActivity())
                            }
                            .addOnFailureListener{showToast(it.message.toString())}
                    }


            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }

}