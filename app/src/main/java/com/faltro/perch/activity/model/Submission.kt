package com.faltro.perch.activity.model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.content
import kotlinx.serialization.json.contentOrNull
import java.time.ZonedDateTime

class Submission(json: JsonObject) {
    val name: String = json["name"]?.content ?: ""
    val displayName: String = json["displayName"]?.content ?: ""
    val authorName: String = json["authorName"]?.content ?: ""
    val description: String = json["description"]?.content ?: ""
    val createTime: ZonedDateTime? = parseZonedDateTime(json["createTime"])
    val updateTime: ZonedDateTime? = parseZonedDateTime(json["updateTime"])
    val gltfUrl: String = json["formats"]?.jsonArray?.get(1)?.jsonObject?.get("root")?.jsonObject?.get("url")?.content
            ?: ""

    private fun parseZonedDateTime(jsonElement: JsonElement?): ZonedDateTime? {
        val text: String? = jsonElement?.contentOrNull

        return if (text != null) {
            ZonedDateTime.parse(text)
        } else {
            null
        }
    }
}