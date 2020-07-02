package com.shukhaev.chatshu.models

import com.google.firebase.auth.PhoneAuthProvider

data class UserModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var phone: String = "",
    var fullname: String = "",
    var state: String = "",
    var photoUrl: String = "empty"
)