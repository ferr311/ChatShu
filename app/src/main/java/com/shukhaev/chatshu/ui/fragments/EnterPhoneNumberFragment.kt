package com.shukhaev.chatshu.ui.fragments

import androidx.fragment.app.Fragment
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.utils.replaceFragment
import com.shukhaev.chatshu.utils.showToast
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    override fun onStart() {
        super.onStart()

        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToast(getString(R.string.register_enter_correct_number))
        } else {
            replaceFragment(EnterCodeFragment())
        }
    }
}