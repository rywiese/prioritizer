package serialization

import kotlinx.serialization.json.JsonElement

interface JsonSerializer<T> {

    fun toJson(t: T): JsonElement

    fun fromJson(json: JsonElement): T

}
