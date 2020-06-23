package com.shukhaev.chatshu.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.databinding.FragmentChatBinding
import com.shukhaev.chatshu.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {


    private lateinit var mBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

    }

}