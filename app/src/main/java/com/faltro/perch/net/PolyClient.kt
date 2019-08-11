package com.faltro.perch.net

import com.faltro.perch.BuildConfig
import java.net.URL

class PolyClient {
    companion object {
        const val BASE_URL: String = "https://poly.googleapis.com/v1/assets"
    }

    fun request(parameters: Map<String, String> = mapOf()): String {
        var paramString = ""
        for (pair in parameters) {
            paramString += "&${pair.key}=${pair.value}"
        }

        return URL("$BASE_URL?key=${BuildConfig.PolyAPIKey}&format=GLTF2&pageSize=40$paramString").readText()
    }
}