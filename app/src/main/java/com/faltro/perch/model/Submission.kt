package com.faltro.perch.model

import kotlinx.serialization.json.*
import java.time.ZonedDateTime

class Submission(json: JsonObject) {
    val name: String = json["name"]?.content ?: ""
    val displayName: String = json["displayName"]?.content ?: ""
    val authorName: String = json["authorName"]?.content ?: ""
    val description: String = json["description"]?.content ?: ""
    val createTime: ZonedDateTime? = parseZonedDateTime(json["createTime"])
    val updateTime: ZonedDateTime? = parseZonedDateTime(json["updateTime"])
    val gltf2Url: String? = parseGltf2Url(json["formats"])

    private fun parseZonedDateTime(jsonElement: JsonElement?): ZonedDateTime? {
        val text: String? = jsonElement?.contentOrNull

        return if (text != null) {
            ZonedDateTime.parse(text)
        } else {
            null
        }
    }

    private fun parseGltf2Url(jsonElement: JsonElement?): String? {
        val formats: JsonArray = jsonElement?.jsonArray ?: return null

        // We currently just assume the GLTF2 format is the last one. If that can no longer be
        // guaranteed, this should be changed to determine the entry with the correct formatType.
        val format = formats.last().jsonObject

        return format["root"]?.jsonObject?.get("url")?.contentOrNull
    }
}