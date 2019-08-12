package com.faltro.perch.model

import android.Manifest

enum class Permission(val str: String, val code: Int) {
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);

    companion object {
        fun getPermissionByStr(str: String) = values().firstOrNull { it.str == str }
        fun getPermissionByName(name: String) = valueOf(name.toUpperCase())
        fun getPermissionByCode(code: Int) = values().firstOrNull { it.code == code }
    }
}