package com.shukhaev.chatshu.models

import com.google.firebase.auth.PhoneAuthProvider

data class User(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var phone: String = "",
    var fullname: String = "",
    var status: String = "",
    var photoUrl: String = ""
)