package com.faltro.perch.model

enum class Complexity(val param: String) {
    ANY("complexity_unspecified"),
    COMPLEX("complex"),
    MEDIUM("medium"),
    SIMPLE("simple");

    companion object {
        fun getComplexityByName(name: String) = valueOf(name.toUpperCase())
        fun getComplexityByParam(param: String) = values().firstOrNull { it.param == param }
    }
}