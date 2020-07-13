package com.shukhaev.chatshu.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST_CODE = 200

fun checkPermission(permission: String): Boolean {
    //функция принимает разрешения и проверяет. Если ещё небыло предоставлено,
    // то запускает диалог с запросом
    return if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
            APP_ACTIVITY,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            APP_ACTIVITY,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
        false
    } else true
}