package com.faltro.perch.net

import com.faltro.perch.BuildConfig
import java.net.URL

class PolyClient {
    companion object {
        const val BASE_URL: String = "https://poly.googleapis.com/v1/assets"
    }

    fun request(parameters: List<Pair<String, String>> = listOf()): String {
        var paramString = ""
        for (pair in parameters) {
            paramString += "&${pair.first}=${pair.second}"
        }

        return URL("$BASE_URL?key=${BuildConfig.PolyAPIKey}&format=GLTF2$paramString").readText()
    }
}