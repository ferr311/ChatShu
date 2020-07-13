package com.shukhaev.chatshu.models

import com.google.firebase.auth.PhoneAuthProvider


//Общая модель для всех сущностей приложения
data class CommonModel(
    //поля юзера
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var phone: String = "",
    var fullname: String = "",
    var state: String = "",
    var photoUrl: String = "empty",

    //поля сообщения
    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Any = ""


) {
    override fun equals(other: Any?): Boolean {
        return (other as CommonModel).id == id
    }
}