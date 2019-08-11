package com.faltro.perch.model

enum class SortType(val param: String) {
    BEST("best"), NEW("newest"), OLD("oldest");

    companion object {
        fun getSortTypeByName(name: String) = valueOf(name.toUpperCase())
        fun getSortTypeByParam(param: String) = values().firstOrNull { it.param == param }
    }
}