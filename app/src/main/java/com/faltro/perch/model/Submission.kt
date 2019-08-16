package com.faltro.perch.model

import kotlinx.serialization.json.*
import java.io.Serializable
import java.time.ZonedDateTime

data class Submission(@Transient val json: JsonObject) : Serializable {
    val name: String = json["name"]?.content ?: ""
    val displayName: String = json["displayName"]?.content ?: ""
    val authorName: String = json["authorName"]?.content ?: ""
    val description: String = json["description"]?.content ?: ""
    val license: String = json["license"]?.content ?: ""
    val createTime: ZonedDateTime? = parseZonedDateTime(json["createTime"])
    val updateTime: ZonedDateTime? = parseZonedDateTime(json["updateTime"])
    val curated: Boolean = json["isCurated"]?.boolean ?: false
    val gltf2Url: String? = parseGltf2Url(json["formats"])
    val thumbnailUrl: String? = json["thumbnail"]?.jsonObject?.get("url")?.content ?: ""
    val pageUrl: String = "https://poly.google.com/${name.replace("assets/", "view/")}"

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