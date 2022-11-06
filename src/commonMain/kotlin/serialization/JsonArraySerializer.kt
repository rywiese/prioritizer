package serialization

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray

interface JsonArraySerializer<T> : JsonSerializer<T> {

    fun toJsonArray(t: T): JsonArray

    fun fromJsonArray(json: JsonArray): T

    override fun toJson(t: T): JsonElement = toJsonArray(t)

    override fun fromJson(json: JsonElement): T = fromJsonArray(json.jsonArray)

}
