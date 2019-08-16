package com.faltro.perch.util

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.faltro.perch.model.Permission

class PermissionHandler {

    /**
     * Retrieve the status of a Permission. If not currently granted, request it.
     *
     * This method returns before the user responds to a permission request. Even if this method
     * returns false, the permission may later be granted. See handleRequestResult for the flow.
     *
     * @param activity the activity with associate context used to present a permission request
     * @param permission the Permission to check or request
     * @return whether the Permission was previously granted
     */
    fun getPermission(activity: AppCompatActivity, permission: Permission): Boolean {
        return if (ContextCompat.checkSelfPermission(activity, permission.str)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission.str), permission.code)
            false
        } else {
            true
        }
    }

    /**
     * Convert code from request result to accepted Permission.
     *
     * ActivityCompat calls an overrideable onRequestPermissionsResult when a permission request is
     * completed, which we override by calling this method to identify the permission granted.
     *
     * Note that this method may be called when a permission request is explicitly denied, but will
     * only return a Permission which has been granted.
     *
     * @param requestCode the internal request code from what we have mapped in Permission
     * @param permissions
     * @param grantResults the grant status of the requested permissions
     * @return the Permission granted, if any, else null
     */
    fun handleRequestResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Permission? {
        return if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Permission.getPermissionByCode(requestCode)
        } else {
            null
        }
    }
}