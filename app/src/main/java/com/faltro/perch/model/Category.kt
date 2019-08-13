package com.faltro.perch.model

enum class Category(val param: String) {
    ALL(""),
    ANIMALS("animals"),
    ARCHITECTURE("architecture"),
    ART("art"),
    CULTURE("culture"),
    FOOD("food"),
    HISTORY("history"),
    NATURE("nature"),
    OBJECTS("objects"),
    PEOPLE("people"),
    SCENES("scenes"),
    SCIENCE("science"),
    TECHNOLOGY("technology"),
    TRANSPORT("transport"),
    TRAVEL("travel");

    companion object {
        fun getCategoryByName(name: String) = valueOf(name.toUpperCase())
        fun getCategoryByParam(param: String) = values().firstOrNull { it.param == param }
    }
}