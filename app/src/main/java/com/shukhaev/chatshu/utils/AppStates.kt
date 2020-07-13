package com.shukhaev.chatshu.utils

import com.shukhaev.chatshu.database.*

enum class AppStates(val state: String) {
    //состояния юзера
    ONLINE("в сети"),
    OFFLINE("был недавно"),
    TYPING("печатает...");

    companion object {
        fun updateState(appStates: AppStates) {
            //функция записывает состояние в базу данных
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(
                    NODE_USERS
                ).child(CURRENT_UID).child(
                    CHILD_STATE
                )
                    .setValue(appStates.state)
                    .addOnSuccessListener { USER.state = appStates.state }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }
}