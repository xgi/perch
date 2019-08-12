package com.faltro.perch.util

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.faltro.perch.model.Permission

class PermissionHandler {

    fun getPermission(activity: AppCompatActivity, permission: Permission): Boolean {
        return if (ContextCompat.checkSelfPermission(activity, permission.str)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission.str), permission.code)
            false
        } else {
            true
        }
    }

    fun handleRequestResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Permission? {
        return if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Permission.getPermissionByCode(requestCode)
        } else {
            null
        }
    }
}