package serialization

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

interface JsonObjectSerializer<T> : JsonSerializer<T> {

    fun toJsonObject(t: T): JsonObject

    fun fromJsonObject(json: JsonObject): T

    override fun toJson(t: T): JsonElement = toJsonObject(t)

    override fun fromJson(json: JsonElement): T = fromJsonObject(json.jsonObject)

}
