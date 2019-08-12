package com.faltro.perch.util

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

class PermissionHandler {
    companion object {
        const val PERMISSION_WRITE_EXTERNAL_STORAGE: Int = 101
    }

    fun getDownloadPermission(activity: AppCompatActivity): Boolean {
        return if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_WRITE_EXTERNAL_STORAGE)
            false
        } else {
            true
        }
    }

    fun handleRequestResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Int {
        return when (requestCode) {
            PERMISSION_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    PERMISSION_WRITE_EXTERNAL_STORAGE
                } else {
                    -1
                }
            }

            else -> {
                -1
            }
        }
    }
}